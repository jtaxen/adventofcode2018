
module aoc06

manhattanDistance(from, to) = sum(map(abs, to - from))

function readInput()
    open("input.txt") do file
        lines = [split(ln, ", ") for ln in eachline(file)]
        x_coords = [ln[1] for ln in lines]
        y_coords = [ln[2] for ln in lines]
        matrix = hcat(x_coords, y_coords)
        map(x -> parse(Int, x), matrix)
    end
end

instructions = readInput()

x_min = minimum(instructions[:, 1])
x_max = maximum(instructions[:, 1])
y_min = minimum(instructions[:, 2])
y_max = maximum(instructions[:, 2])
width = x_max - x_min
height = y_max - y_min


function drawMap(instructions, width, height)

    matrix = zeros(Int, width, height)
    w, h = size(matrix)
    for i in 1:w
        for j in 1:h
            dist = map(x -> manhattanDistance([i, j], x), [instructions[i,:] for i in 1:size(instructions, 1)])
            mindist = findall(x -> x == minimum(dist), dist)
            if length(mindist) == 1
                matrix[i, j] = mindist[1]
            else
                matrix[i, j] = -1
            end
        end
    end
    matrix
end

matrixMap = drawMap(instructions, width, height)

infiniteSets = union(Set(matrixMap[1,:]),
                     Set(matrixMap[end,:]),
                     Set(matrixMap[:,1]),
                     Set(matrixMap[:,end]))

finiteSets = setdiff(Set(1:size(instructions, 1)), infiniteSets)

function getAreas(matrix, finites)

    arrayOfSets = collect(finites)
    map(x -> count(y -> x == y, matrix), arrayOfSets)
end

println("The biggest area is:")
println(maximum(getAreas(matrixMap, finiteSets)))

function drawSecondMap(instructions, width, height)

    matrix = zeros(Int, width, height)
    w, h = size(matrix)
    for i in 1:w
        for j in 1:h
            dist = map(x -> manhattanDistance([i, j], x), [instructions[i,:] for i in 1:size(instructions, 1)])
            if sum(dist) < 10000
                matrix[i, j] = 1
            else
                matrix[i, j] = -1
            end
        end
    end
    matrix
end

safeMap = drawSecondMap(instructions, width, height)
safeAreaSize = count(x -> x == 1, safeMap)

println("Area of close points:")
println(safeAreaSize)

end # module
