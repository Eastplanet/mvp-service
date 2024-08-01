import heapq

# 지도
mapGrid = [
    ['4', 'o', '12', 'o', '8'],
    ['x', 'x', 'o', 'x', 'x'],
    ['3', 'o', '11', 'o', '7'],
    ['x', 'x', 'o', 'x', 'x'],
    ['2', 'o', '10', 'o', '6'],
    ['x', 'x', 'o', 'x', 'x'],
    ['1', 'o', '9', 'o', '5'],
]

# 방향: (행 이동, 열 이동)
directions = {
    'right': (0, 1),
    'down': (1, 0),
    'left': (0, -1),
    'up': (-1, 0)
}

def dijkstra(mapGrid, start, goal, initialDirection):
    """Dijkstra의 경로 탐색 알고리즘 (방향 포함)"""
    rows, cols = len(mapGrid), len(mapGrid[0])
    queue = []
    heapq.heappush(queue, (0, start, initialDirection))
    cameFrom = {start: (None, initialDirection)}
    costSoFar = {start: 0}
    goal = (goal[0], goal[1])

    while queue:
        currentCost, current, currentDir = heapq.heappop(queue)

        if current == goal:
            break

        for directionName, direction in directions.items():
            nextPos = (current[0] + direction[0], current[1] + direction[1])
            if 0 <= nextPos[0] < rows and 0 <= nextPos[1] < cols and mapGrid[nextPos[0]][nextPos[1]] != 'x':
                if directionName == currentDir:
                    turnCost = 0  # 직진 이동 시 회전 비용 없음
                    moveType = 'straight'
                else:
                    # 좌회전 또는 우회전인지 결정
                    if ((currentDir == 'right' and directionName == 'down') or
                        (currentDir == 'down' and directionName == 'left') or
                        (currentDir == 'left' and directionName == 'up') or
                        (currentDir == 'up' and directionName == 'right')):
                        moveType = 'right'
                    else:
                        moveType = 'left'
                    turnCost = 1  # 회전 시 비용 추가

                newCost = currentCost + 1 + turnCost
                if nextPos not in costSoFar or newCost < costSoFar[nextPos]:
                    costSoFar[nextPos] = newCost
                    heapq.heappush(queue, (newCost, nextPos, directionName))
                    cameFrom[nextPos] = (current, moveType)

    return reconstructPath(cameFrom, start, goal)

def reconstructPath(cameFrom, start, goal):
    """시작 지점에서 목표 지점까지의 경로 재구성 (방향 포함)"""
    current = goal
    path = []
    while current != start:
        if current not in cameFrom:
            return []  # 유효한 경로를 찾을 수 없음
        prevNode, moveType = cameFrom[current]
        path.append((current, moveType))
        current = prevNode
    path.append((start, cameFrom[start][1]))  # 초기 방향 포함
    path.reverse()
    return path

# 특정 숫자의 좌표를 찾는 함수
def findCoordinates(mapGrid, number):
    for i, row in enumerate(mapGrid):
        for j, cell in enumerate(row):
            if cell == str(number):
                return (i, j)
    return None

# 경로 찾기
def findShortestPathToGoal(mapGrid, startNode, endNode, initialDirection):
    start = findCoordinates(mapGrid, startNode)
    goal = findCoordinates(mapGrid, endNode)
    if start and goal:
        path = dijkstra(mapGrid, start, goal, initialDirection)
        return path
    return []

# 출발지, 도착지 설정
startNode = 1
endNode = 8
initialDirection = 'right'  # 초기 방향
path = findShortestPathToGoal(mapGrid, startNode, endNode, initialDirection)
print(f'node({startNode}) -> node({endNode}) :', path)
