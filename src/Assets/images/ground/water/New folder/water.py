import numpy as np
block_size = 8
s = None
import pygame
pygame.init()

def render(arr:np.ndarray):
    global s
    size = arr.shape
    if s is None:
        s = pygame.display.set_mode((size[0] * block_size, size[1] * block_size))
    mapSurf.lock()
    for y in range(map.shape[0]):
        for x in range(map.shape[1]):
            cell = map[x][y]
            if cell > threshold:
                mapSurf.set_at((x,y),(0,0,255))
            else:
                mapSurf.set_at((x,y),(200,200,255))
    mapSurf.unlock()
    s.blit(pygame.transform.scale_by(mapSurf,block_size),(0,0))
    pygame.display.flip()
import opensimplex
map = np.zeros((64,64),np.bool_)
mapSurf = pygame.Surface(map.shape)
scale = .1
xOffset = 0
yOffset = 0
threshold = 0.5
def makeMap():
    global map
    #map = map
    map = opensimplex.noise2array(np.arange(map.shape[0])*scale+yOffset,np.arange(map.shape[1])*scale+xOffset)**2 
render(map)
#time.sleep(2)
clock = pygame.time.Clock()
refresh = True
while True:
    for event in pygame.event.get():
        if event.type == pygame.QUIT:
            print('scale',scale)
            print('xOffset',xOffset)
            print('yOffset',yOffset)
            print('threshold',threshold)
            quit()
        elif event.type == pygame.MOUSEBUTTONDOWN:
            pass
        elif event.type == pygame.KEYDOWN:
            if event.key == pygame.K_SPACE:
                print('doint hing')
            if event.key == pygame.K_DOWN:
                threshold -= 0.03
                refresh = True

            elif event.key == pygame.K_UP:
                threshold += 0.03
                refresh = True




    keys =  pygame.key.get_pressed()
    xOffset += (keys[pygame.K_d] - keys[pygame.K_a]) * scale * 0.1
    yOffset += (keys[pygame.K_s] - keys[pygame.K_w]) * scale * 0.1
    if (keys[pygame.K_d] or keys[pygame.K_a]) or (keys[pygame.K_s] or keys[pygame.K_w]):
        refresh = True
 
    if refresh:
        makeMap()
        render(map)
        refresh = False
    clock.tick(30)

        


