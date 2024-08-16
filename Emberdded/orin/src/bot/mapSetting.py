from .pathFinding import dijkstra
from .utils import findCoordinates

# 지도
mapGrid = [
    ['4', 'x', '5', 'x', '6', 'x', '7'],
    ['o', 'o', 'o', 'o', 'o', 'o', 'o'],
    ['0', 'x', '1', 'x', '2', 'x', '3']
]

def findShortestPathToGoal(mapGrid, startNode, endNode, initialDirection):
    # startNode가 0일 때만 매핑을 적용
    start = findCoordinates(mapGrid, startNode)
    goal = findCoordinates(mapGrid, endNode)
    
    if start and goal:
        path = dijkstra(mapGrid, start, goal, initialDirection)
        return path + ['Straight', 'Stop']
    return []