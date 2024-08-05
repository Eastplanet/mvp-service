from .pathFinding import dijkstra
from .utils import findCoordinates

# 지도
mapGrid = [
    ['7', 'x', '6', 'x', '5', 'x', '4'],
    ['o', '9', 'o', 'o', 'o', '8', 'o'],
    ['3', 'x', '2', 'x', '1', 'x', '0']
]


def findShortestPathToGoal(mapGrid, startNode, endNode, initialDirection):
    start = findCoordinates(mapGrid, startNode)
    goal = findCoordinates(mapGrid, endNode)
    
    if start and goal:
        path = dijkstra(mapGrid, start, goal, initialDirection) + ['Straight', 'Stop']
        return path
    return []