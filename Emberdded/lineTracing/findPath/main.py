from pathfinding import dijkstra
from utils import findCoordinates

# 지도
mapGrid = [
    ['7', 'x', '6', 'x', '5', 'x', '4'],
    ['o', '9', 'o', 'o', 'o', '8', 'o'],
    ['3', 'x', '2', 'x', '1', 'x', '0']
]

# endNode에 따라 실제 목표 노드를 결정하는 매핑
end_node_mapping = {
    1: 5,
    2: 6,
    3: 7,
    4: 8,
    5: 1,
    6: 2,
    7: 3
}

def findShortestPathToGoal(mapGrid, startNode, endNode, initialDirection):
    # startNode가 0일 때만 매핑을 적용
    if startNode == 0:
        actualEndNode = end_node_mapping.get(endNode, endNode)
    else:
        actualEndNode = endNode
    
    start = findCoordinates(mapGrid, startNode)
    goal = findCoordinates(mapGrid, actualEndNode)
    
    if start and goal:
        path = dijkstra(mapGrid, start, goal, initialDirection)
        return path
    return []

# 출발지, 도착지 설정
startNode = 1
endNode = 0
initialDirection = 'up'  # 초기 방향
path = findShortestPathToGoal(mapGrid, startNode, endNode, initialDirection)
print(f'node({startNode}) -> node({endNode}) (mapped to {end_node_mapping.get(endNode, endNode)}) :', path)
