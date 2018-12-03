from collections import Counter
from numpy import array

def getIds():
    with open("input.txt", "r") as f:
        return array([c.strip() for c in f.readlines()])


def countRepeatedLetters(string):
    counter = Counter(string)
    counts = counter.values()
    return array([2 in counts, 3 in counts])

def getChecksum(ids):
    counts = array([countRepeatedLetters(c) for c in ids])
    twos = array([c[0] for c in counts])
    threes = array([c[1] for c in counts])
    return twos.sum() * threes.sum()

def compare(ids):
    length = len(ids)
    for i, item in enumerate(ids):
        for j in range(i, length):
            comp_str = ids[j]
            result = ""
            for k, c in enumerate(item):
                if c != comp_str[k]:
                    result += c
            if len(result) == 1:
                return item, comp_str

def charsInCommon(a, b):
    result = ""
    for i, char in enumerate(a):
        if char == b[i]:
            result += char
    return result

if __name__ == "__main__":
    ids = getIds()
    print("Checksum: %d" % getChecksum(ids))

    a, b = compare(ids)
    print("The id is %s" % charsInCommon(a, b))


