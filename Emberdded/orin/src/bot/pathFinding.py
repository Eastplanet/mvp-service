import heapq

# 이동: (행 이동, 열 이동)
moves = {
    'right': (0, 1),
    'down': (1, 0),
    'left': (0, -1),
    'up': (-1, 0)
}

def dijkstra(mapGrid, start, goal, initialDirection):
    """Dijkstra's pathfinding algorithm with direction consideration."""
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

        for moveName, move in moves.items():
            nextPos = (current[0] + move[0], current[1] + move[1])
            if 0 <= nextPos[0] < rows and 0 <= nextPos[1] < cols and mapGrid[nextPos[0]][nextPos[1]] != 'x':
                if moveName == currentDir:
                    turnCost = 0  # No turn cost for straight movement
                    moveType = 'straight'
                else:
                    # Determine if it is a left or right turn
                    if ((currentDir == 'right' and moveName == 'down') or
                        (currentDir == 'down' and moveName == 'left') or
                        (currentDir == 'left' and moveName == 'up') or
                        (currentDir == 'up' and moveName == 'right')):
                        moveType = 'right'
                    else:
                        moveType = 'left'
                    turnCost = 1  # Cost for turning

                newCost = currentCost + 1 + turnCost
                if nextPos not in costSoFar or newCost < costSoFar[nextPos]:
                    costSoFar[nextPos] = newCost
                    heapq.heappush(queue, (newCost, nextPos, moveName))
                    cameFrom[nextPos] = (current, moveType)

    return reconstructPath(cameFrom, start, goal)

def reconstructPath(cameFrom, start, goal):
    """Reconstructs the path from the goal to the start, including the direction of movement."""
    current = goal
    path = []
    straightNum = 0
    while current != start:
        if current not in cameFrom:
            return []  # No valid path found
        prevNode, moveType = cameFrom[current]
        
        # Determine the current direction based on the movement direction to this node
        if prevNode is not None:
            if moveType == 'straight':
                straightNum += 1
                action = 'Straight'
            else:
                straightNum = 0
                if moveType == 'left':
                    action = 'Rotate left'
                elif moveType == 'right':
                    action = 'Rotate right'
                else:
                    action = 'Unknown action'
            path.append(action)

            # if action != 'Straight' or straightNum % 2 == 1:
            #     path.append(action)
        current = prevNode
    path.reverse()
    return path