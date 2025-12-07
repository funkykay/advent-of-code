#include <iostream>
#include <fstream>
#include <string>
#include <list>
#include <iterator>
#include <utility>

template<typename Container>
void insert_interval(Container& intervals, long new_start, long new_end)
{
    for (auto cur = intervals.begin(); cur != intervals.end(); ++cur) {
        const auto cur_start = cur->first;
        const auto cur_end   = cur->second;

        if (new_start > cur_end + 1) {
            continue;
        }
        if (new_end < cur_start - 1) {
            intervals.insert(cur, std::make_pair(new_start, new_end));
            return;
        }
        if (new_start < cur->first) {
            cur->first = new_start;
        }
        if (new_end > cur->second) {
            cur->second = new_end;
        }
        auto next = std::next(cur);
        while (next != intervals.end() && next->first <= cur->second + 1) {
            if (next->second > cur->second) {
                cur->second = next->second;
            }
            next = intervals.erase(next);
        }
        return;
    }
    intervals.emplace_back(new_start, new_end);
}


bool covers(const std::list<std::pair<long, long>>& intervals, long value)
{
    for (const auto& iv : intervals) {
        if (value < iv.first) {
            return false;
        }
        if (value <= iv.second) {
            return true;
        }
    }
    return false;
}


int main() {
    std::ifstream file("2025/day05/input.txt");
    if (!file.is_open()) {
        std::cerr << "Konnte Datei nicht öffnen!\n";
        return 1;
    }

    std::list<std::pair<long, long>> ids;

    long totalFresh = 0;

    std::string line;
    while (std::getline(file, line)) {

        size_t pos = line.find('-');
        if (pos != std::string::npos) {
            std::string left  = line.substr(0, pos);
            std::string right = line.substr(pos + 1);

            long L = std::stol(left);
            long R = std::stol(right);
            insert_interval(ids, L, R);

        } else {
            if (line.empty()) {
                continue;
            }

            long value = std::stol(line);
            if (covers(ids, value)) {
                totalFresh+=1;
            }
        }
    }

    // for(const auto& p : ids) {
    //     std::cout << "(" << p.first << ", " << p.second << ")\n";
    // }
    std::cout << "Total Fresh: " << totalFresh << '\n';

    return 0;
}
