import time
from motorControl import initServoAngle, steerServoAngle

def findCoordinates(mapGrid, number):
    """Find the coordinates of a specific number in the map grid."""
    for i, row in enumerate(mapGrid):
        for j, cell in enumerate(row):
            if cell == str(number):
                return (i, j)
    return None