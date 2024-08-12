facing_directions = ["north", "east", "south", "west"]
contents_variants = ["empty", "oak_sapling", "spruce_sapling", "birch_sapling", "jungle_sapling", "acacia_sapling", "dark_oak_sapling", "cherry_sapling"]
wire_variants = ["true", "false"]

json_data = {"variants": {}}

for facing in facing_directions:
    for content in contents_variants:
        for wire in wire_variants:
            rotation = 0
            if facing == "east":
                rotation = 90
            elif facing == "south":
                rotation = 180
            elif facing == "west":
                rotation = 270

            if content == "empty":
                model = f"ericslittletrees:block/bonsai_pot"
            elif wire == "true":
                model_name = content.replace("_sapling", "")
                model = f"ericslittletrees:block/pottedbonsai/{model_name}_bonsai"
            else:
                model_name = content
                model = f"ericslittletrees:block/pottedsapling/{model_name}_bonsai"
            
            json_data["variants"][f"has_wire={wire},contents={content},facing={facing}"] = {
                "model": model,
                "y": rotation
            }

import json
print(json.dumps(json_data, indent=2))
