from pathfinding import dijkstra
from utils import findCoordinates

# 지도
mapGrid = [
    ['4', 'x', '5', 'x', '6', 'x', '7'],
    ['o', '8', 'o', 'o', 'o', '9', 'o'],
    ['0', 'x', '1', 'x', '2', 'x', '3']
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
        if startNode == 0:
            if endNode == 4:
                path += ['Back', 'Rotate left']
            path += ['Back', 'Back']
        elif endNode == 0:
            path += ['Straight']
        return path + ['Stop']
    return []

# 출발지, 도착지 설정
for endNode in range(1, 8):
    startNode = 0
    initialDirection = 'up'  # 초기 방향
    path = findShortestPathToGoal(mapGrid, startNode, endNode, initialDirection)
    print(f'node({startNode}) -> node({endNode}) (mapped to {end_node_mapping.get(endNode, endNode)}) :', path)
print()

for startNode in range(1, 8):
    endNode = 0
    initialDirection = 'up' if startNode < 4 else 'down'  # 초기 방향
    path = findShortestPathToGoal(mapGrid, startNode, endNode, initialDirection)
    print(f'node({startNode}) -> node({endNode}) (mapped to {end_node_mapping.get(endNode, endNode)}) :', path)
