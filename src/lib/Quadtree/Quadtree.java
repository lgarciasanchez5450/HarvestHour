package lib.Quadtree;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import GameEngine.Physics.Physical;
import GameEngine.Player;

/**
 * Java Quadtree Implementation made by LEOGS :)
 */

public class Quadtree {
    class Node {
        private Physical[] objects = new Physical[MAX_LOAD];
        private Node[] nodes; // These are ordered like from left to right , top - down [0,1,2,3]

        public String toString() {
            int i = 0;
            while (objects != null && i < 4 && objects[i] !=null ) {i++;}
            return "Node{split:" +(nodes!=null) + ",objectCount:" + i + "}";
        }

    }
    public static final int MAX_LOAD = 4;
    private Node root;
    private Rect boundary;
    public Quadtree(float minX, float minY, float width, float height) {
        root = new Node();
        boundary = new Rect(minX,minY,width,height);
    }
    public boolean insert(@NotNull Physical object) {
        if (!boundary.containsPoint(object.getWorldX(),object.getWorldY())) return false;
        insert(object,root,boundary);
        return true;
    }
    private void insert(Physical obj, Node node, Rect nodeBoundary) {
        if (node.nodes != null) { // is Split
            int index = (int)(2 * (obj.getWorldY() - nodeBoundary.getY()) / nodeBoundary.getHeight()) * 2 +
                        (int)(2 * (obj.getWorldX() - nodeBoundary.getX()) / nodeBoundary.getWidth());
            insert(obj,node.nodes[index],getSubBoundaryFromIndex(nodeBoundary,index));

        } else {
            for (int i = 0; i < node.objects.length; i++) {
                if (node.objects[i] == null) {
                    node.objects[i] = obj;
                    return;
                }
            }
            // It did not fit into the objects array and node must split
            node.nodes = new Node[]{new Node(), new Node(), new Node(), new Node()};
            for (Physical object : node.objects) {
                insert(object,node,nodeBoundary);
            }
            insert(obj,node,nodeBoundary);
            node.objects = null;
        }
    }
    public List<Physical> getRectIntersect(@NotNull Rect rect) {
        ArrayList<Physical> t = new ArrayList<>();
        rectIntersect(rect,root,boundary,t);
        return t;
    }
    private void rectIntersect(Rect rect,Node node, Rect nodeBoundary,List<Physical> t) {
        if (node.nodes != null) {
            for (int i = 0; i < 4; i++) {
                Rect subBoundary = getSubBoundaryFromIndex(nodeBoundary,i);
                if (rect.intersectRect(subBoundary)) {
                    rectIntersect(rect,node.nodes[i],subBoundary,t);
                }
            }
        } else {
            for (Physical obj : node.objects) {
                if (obj == null) return;
                if (rect.containsPoint(obj.getWorldX(),obj.getWorldY())) {
                    t.add(obj);
                }
            }
        }

    }

    private boolean remove(Physical obj) {
        if (!boundary.containsPoint(obj.getWorldX(),obj.getWorldY())) return false;
        return remove(obj,root,boundary);
    }
    private boolean remove(Physical obj, Node node, Rect nodeBoundary) {
        if (node.nodes != null) { // is Split
            int index = (int) (2 * (obj.getWorldY() - nodeBoundary.getY()) / nodeBoundary.getHeight()) * 2 +
                    (int) (2 * (obj.getWorldX() - nodeBoundary.getX()) / nodeBoundary.getWidth());
            return remove(obj, node.nodes[index], getSubBoundaryFromIndex(nodeBoundary, index));
        } else {
            int i = 0;
            while (i < MAX_LOAD && node.objects[i] != obj) {i++;}
            if (i==MAX_LOAD) return false;
            while (i < MAX_LOAD-1) {
                node.objects[i] = node.objects[i+1];
                i++;
            }
            node.objects[i] = null;
            return true;
        }
    }

    private Rect getSubBoundaryFromIndex(Rect boundary, int index) {
        float halfWidth = boundary.getWidth() / 2;
        float halfHeight = boundary.getHeight() / 2;
        return switch (index) {
            case 0 -> new Rect(boundary.getX(), boundary.getY(), halfWidth, halfHeight);
            case 1 -> new Rect(boundary.getX() + halfWidth, boundary.getY(), halfWidth, halfHeight);
            case 2 -> new Rect(boundary.getX(), boundary.getY() + halfHeight, halfWidth, halfHeight);
            default -> new Rect(boundary.getX() + halfWidth, boundary.getY() + halfHeight, halfWidth, halfHeight);
        };
    }

    public String toString() {
        return toString(root,2);
    }
    private String toString(Node node,int depth) {
        String nodeString = node.toString();
        if (node.nodes != null) {
            for (Node n : node.nodes) {
                nodeString += "\n";
                for (int i = 0; i < depth;i++) nodeString += " ";
                nodeString += toString(n,depth+2);
            }
        }
        return nodeString;
    }

    public static void main(String[] args) {
        Quadtree tree = new Quadtree(0,0,100,100);
        Player p =  new Player(1,1);
        tree.insert(p);
        tree.insert( new Player(1,2));
        tree.insert( new Player(1,4));
        tree.insert( new Player(1,6));
        tree.insert( new Player(1,3));
        System.out.println(tree);
        tree.remove(p);
        System.out.println(tree);

    }
}


