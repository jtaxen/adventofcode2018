import numpy as np
from numpy import array
from time import clock

def readInstructions():

    with open('input.txt', 'r') as f:
        return array([l for l in f.readlines()])



def getCoordinates(instructions):
    def cleanCoordinates(line):
        coordinates = line.split(" ")[2].split(",")
        coordinates[1] = coordinates[1][:-1]
        return array([int(coordinates[0]), int(coordinates[1])])

    return array([cleanCoordinates(c) for c in instructions])


def getSizes(instructions):
    def cleanSize(line):
        coordinates = line.split(" ")[3].split("x")
        return array([int(coordinates[0]), int(coordinates[1].strip())])

    return array([cleanSize(c) for c in instructions])

def getIds(instructions):
    def cleanId(line):
        return int(line.split(" ")[0].strip("#"))

    return array([cleanId(i) for i in instructions])

def getExtremes(coordinates, sizes):
    sums = coordinates + sizes
    x_coords = [item[0] for item in sums]
    y_coords = [item[1] for item in sums]
    return (max(x_coords), max(y_coords))


def makeFabric(instructions):

    ids_of_overlaps = lambda x, y: y if x == 0 else -1

    coordinates = getCoordinates(instructions)
    sizes = getSizes(instructions)
    identities = getIds(instructions)
    overlaps = set()

    fabric = np.zeros(getExtremes(coordinates, sizes))
    for i, coordinate in enumerate(coordinates):
        x_origin = coordinate[0]
        y_origin = coordinate[1]
        size = sizes[i]
        identity = identities[i]
        for x in range(x_origin, x_origin + size[0]):
            for y in range(y_origin, y_origin + size[1]):
                oldCell = fabric[x][y]
                newCell = ids_of_overlaps(oldCell, identity)
                if oldCell != 0:
                    overlaps.add(identity)
                    overlaps.add(oldCell)
                fabric[x][y] = newCell

    return fabric, overlaps


if __name__ == "__main__":
    start_time = clock()

    for _ in range(10):
        instructions = readInstructions()

        fabric, overlaps = makeFabric(instructions)
        print("Number of overlapping items is %d" % (fabric == -1).sum())
        ids = set(getIds(instructions))
        diff = ids.difference(overlaps)
        print("The non-overlapping item is #%s" % diff)
    end_time = clock()
    print("~~~~~ Execution time: %f seconds ~~~~~" % ((end_time - start_time)/10))
