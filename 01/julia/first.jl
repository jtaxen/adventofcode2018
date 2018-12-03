function getInstructions()
    open("input.txt") do file
        return [parse(Int, ln) for ln in eachline(file)]
    end
end

instructions = getInstructions()

finalFreq = reduce(+, instructions)
println("Final frequency:")
println(finalFreq)


function whileloop(instructions)
    passedFrequencies = []
    inst = instructions
    current = 0
    while true
        current += inst[1]
        if current in passedFrequencies
            println("Match:")
            println(current)
            break
        else
            push!(passedFrequencies, current)
            if length(inst) < 1
                print("linth")
                inst = instructions
            else
                inst = popfirst!(inst)
            end
        end
    end
end

whileloop(instructions)
