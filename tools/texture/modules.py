import math
import sys
from pathlib import Path

from PIL import Image

ROOT = Path(__file__).resolve().parents[2]
TARGETS = [ROOT / 'versions' / version / 'src/main/resources/assets/modular_angelring/textures/item' for version in ('1.21.1', '26.1.2')]

GOLD = ['#815110', '#ab7416', '#dda414', '#f4d228', '#ffe874', '#fdff90', '#ffffe3']
TONES = {str(i + 1): tuple(int(h[k:k + 2], 16) for k in (1, 3, 5)) + (255,) for i, h in enumerate(GOLD)}

CHIP = [
    '................',
    '................',
    '...3333333333...',
    '..377677655553..',
    '..376555555552..',
    '..366544444442..',
    '..366444444442..',
    '..366444444442..',
    '..366444444442..',
    '..365444444442..',
    '..255444444442..',
    '..255444444441..',
    '..155444445521..',
    '..122222222221..',
    '...1111111111...',
    '................',
]

CENTER = (7.5, 8.0)
FACE_X = range(4, 13)
FACE_Y = range(4, 12)

EMBLEMS = {
    'step_assist': ([
        '........',
        '......##',
        '......##',
        '...#####',
        '...#####',
        '########',
        '########',
        '########',
    ], {}),
    'reach': ([
        '..#.#...',
        '#.#.#.#.',
        '#.#.#.#.',
        '#######.',
        '#######.',
        '########',
        '.######.',
        '..####..',
    ], {(7, 5): '2'}),
    'phantom_ward': ([
        '........',
        '#.......',
        '#.......',
        '###....#',
        '########',
        '########',
        '#......#',
        '........',
    ], {(3, 3): '4', (7, 6): '2', (8, 7): '5'}),
    'void_guardian': ([
        '..####..',
        '.######.',
        '########',
        '#......#',
        '.#....#.',
        '..#..#..',
        '..####..',
        '..####..',
    ], {(8, 1): '5'}),
    'aqua_lung': ([
        '.##..##.',
        '.##.#..#',
        '....#..#',
        '.....##.',
        '.##.....',
        '#..#....',
        '#..#....',
        '.##.....',
    ], {(2, 1): '1', (5, 1): '6', (6, 1): '6', (5, 2): '4', (6, 2): '4', (1, 5): '6', (2, 5): '6', (1, 6): '4', (2, 6): '4'}),
    'fast_build': ([
        '....####',
        '....####',
        '........',
        '#.###.##',
        '#.###.##',
        '........',
        '###.####',
        '###.####',
    ], {(1, 5): '5', (5, 5): '5', (7, 4): '1', (8, 2): '5', (8, 5): '5'}),
}


def placement(rows):
    cells = [(x, y) for y, row in enumerate(rows) for x, key in enumerate(row) if key != '.']
    min_x, max_x = min(x for x, _ in cells), max(x for x, _ in cells)
    min_y, max_y = min(y for _, y in cells), max(y for _, y in cells)
    origin_x = math.floor(CENTER[0] - (max_x - min_x) / 2) - min_x
    origin_y = math.floor(CENTER[1] - (max_y - min_y) / 2) - min_y
    return origin_x, origin_y


def emblem_cells(rows, origin):
    return {(origin[0] + x, origin[1] + y): key for y, row in enumerate(rows) for x, key in enumerate(row) if key != '.'}


def engrave(grid, cells):
    mask = set(cells)
    for (x, y), key in cells.items():
        if key == '#':
            in_shadow = (x, y - 1) not in mask or (x - 1, y) not in mask
            grid[y][x] = '1' if in_shadow else '2'
        else:
            grid[y][x] = key
    for x, y in mask:
        for lx, ly in ((x + 1, y), (x, y + 1)):
            if (lx, ly) not in mask and lx in FACE_X and ly in FACE_Y:
                grid[ly][lx] = '5'


def module(rows, overrides):
    grid = [list(row) for row in CHIP]
    origin = placement(rows)
    engrave(grid, emblem_cells(rows, origin))
    for (x, y), key in overrides.items():
        grid[origin[1] + y][origin[0] + x] = key
    image = Image.new('RGBA', (16, 16), (0, 0, 0, 0))
    for y, row in enumerate(grid):
        for x, key in enumerate(row):
            if key != '.':
                image.putpixel((x, y), TONES[key])
    return image


def main():
    targets = [Path(arg) for arg in sys.argv[1:]] or TARGETS
    for name, (rows, overrides) in EMBLEMS.items():
        image = module(rows, overrides)
        for target in targets:
            target.mkdir(parents=True, exist_ok=True)
            image.save(target / f'{name}_module.png')


if __name__ == '__main__':
    main()
