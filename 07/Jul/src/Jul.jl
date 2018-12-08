module Jul

function readFile()
    function splitLine(ln)
        sLn = split(ln)
        (string(sLn[2]), string(sLn[8]))
    end

    open("input.txt") do file
        map(splitLine, eachline(file))
    end
end


instructions = readFile()
steps = sort(collect(union(Set([a[1] for a in instructions]),
                           Set([a[2] for a in instructions]))))



getPossibleSteps(steps, instructions) = [s for s in steps if all(ln[2] != s for ln in instructions)]

function eliminate(steps, instructions)

    orderedSteps = ""
    while length(steps) > 0
        possibleSteps = sort(getPossibleSteps(steps, instructions))
        nextStep = possibleSteps[1]
        orderedSteps = string(orderedSteps, nextStep)
        steps = [s for s in steps if s != nextStep]
        instructions = filter(x -> x[1] != nextStep, instructions)
    end
    orderedSteps
end

println(eliminate(steps, instructions))

function secondThing(steps, instructions, numOfWorkers)

    time(c) = 60 + Int(c) - Int('A')
    t = 0
    workers = [0 for _ in 1:numOfWorkers]
    work = [-1 for _ in 1:numOfWorkers]

    while length(steps) > 0 && any(w > 0 for w in workers)
        possibleStep = sort(getPossibleSteps(steps, instructions))
        if length(possibleStep) > 0
            nextStep = possibleStep[end]
        else
            nextStep = -1
        end

        for i in 1:numOfWorkers
            workers[i] = max(workers[i] - 1, 0)
            if workers[i] == 0
                if work[i] != -1
                    instructions = [ln for ln in instructions if ln[1] != work[i]]
                end
                if nextStep > -1
                    n = pop!(possibleStep)
                    workers[i] = time(n)
                    work[i] = n
                    steps = [s for s in steps if s != nextStep]
                end
            end
        t += 1
        end
    end
    return t
end

println(secondThing(steps, instructions, 5))




end # module
