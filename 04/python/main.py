import re
import numpy
from numpy import array

def read_list():

    with open("input.txt", "r") as f:
        return sorted([l for l in f.readlines()])

def get_guard_list(instructions):
    return sorted(list({int(re.search(r"Guard #(\d+)", guard).group(1))
            for guard in instructions
            if re.search(r"Guard #\d+", guard)}))

def get_minute(string):
    return int(re.search(r"\d{2}:(\d{2})", string).group(1))

def get_sleep_schedule(instructions, guards):

    schedule = numpy.zeros((len(guards), 60), dtype=int)
    guard_index = 0
    falls_asleep = 0
    for instruction in instructions:
        re_guard_id = re.search(r"Guard #(\d+)", instruction)
        if re_guard_id:
            guard_index = guards.index(int(re_guard_id.group(1)))
            continue
        if "falls asleep" in instruction:
            falls_asleep = get_minute(instruction)
            continue
        if "wakes up" in instruction:
            wakes_up = get_minute(instruction)
            for t in range(falls_asleep, wakes_up):
                schedule[guard_index][t] += 1
    return schedule

def get_sleepiest_guard(schedule, guards):
    guards_sleep = list(map(sum, schedule))
    sg_index = guards_sleep.index(max(guards_sleep))
    return sg_index

def get_sleepiest_minute(sleep_schedule):
    return sleep_schedule.argmax()

def get_sleepiest_minutes(sleep_schedule):
    return numpy.unravel_index(sleep_schedule.argmax(), sleep_schedule.shape)




def main():
    instructions = read_list()
    guard_list = get_guard_list(instructions)
    sleep_schedule = get_sleep_schedule(instructions, guard_list)

    sg_index = get_sleepiest_guard(sleep_schedule, guard_list)
    sleepiest_guard = guard_list[sg_index]
    sleepiest_minute = get_sleepiest_minute(sleep_schedule[sg_index])

    print("The sleepiest guard is #%i" % sleepiest_guard)
    print("His/her sleepiest minute is 00:%i" % sleepiest_minute)
    print("The product is %i" % (sleepiest_guard * sleepiest_minute))

    sleepiest_index = get_sleepiest_minutes(sleep_schedule)
    sleepiest_guard = guard_list[sleepiest_index[0]]
    print("The guard that sleeps most often a certain minute is #%i" % sleepiest_guard)
    print("His/her sleepiest minute is 00:%i" % sleepiest_index[1])
    print("The product is %i" % (sleepiest_guard * sleepiest_index[1]))

if __name__ == "__main__":
    main()
