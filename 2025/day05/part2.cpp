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

int main() {
    std::ifstream file("2025/day05/input.txt");
    if (!file.is_open()) {
        std::cerr << "Konnte Datei nicht öffnen!\n";
        return 1;
    }

    std::list<std::pair<long, long>> ids;

    std::string line;
    while (std::getline(file, line)) {

        size_t pos = line.find('-');
        if (pos != std::string::npos) {
            std::string left  = line.substr(0, pos);
            std::string right = line.substr(pos + 1);

            long L = std::stol(left);
            long R = std::stol(right);
            insert_interval(ids, L, R);
        }
    }

    long long totalFresh = 0;
    for (const auto& p : ids) {
        totalFresh += static_cast<long long>(p.second) - static_cast<long long>(p.first) + 1;
    }
    std::cout << "Total Fresh: " << totalFresh << '\n';

    return 0;
}
