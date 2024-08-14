facing_directions = ["north", "east", "south", "west"]
contents_variants = ["empty", "oak_sapling", "spruce_sapling", "birch_sapling", "jungle_sapling", "acacia_sapling", "dark_oak_sapling", "cherry_sapling", "mangrove_sapling"]
wire_variants = ["true", "false"]
bonsai_variants = ["true", "false"]

json_data = {"variants": {}}

for facing in facing_directions:
    for content in contents_variants:
        for wire in wire_variants:
            for bonsai in bonsai_variants:
                rotation = 0
                if facing == "east":
                    rotation = 90
                elif facing == "south":
                    rotation = 180
                elif facing == "west":
                    rotation = 270

                if content == "empty":
                    model = f"ericslittletrees:block/bonsai_pot"
                elif bonsai == "true":
                    model_name = content.replace("_sapling", "")
                    model = f"ericslittletrees:block/pottedbonsai/basic/{model_name}_bonsai"
                elif wire == "true":
                    model_name = content
                    model = f"ericslittletrees:block/pottedwiredsapling/{model_name}_bonsai"
                else:
                    model_name = content
                    model = f"ericslittletrees:block/pottedsapling/{model_name}_bonsai"
                
                json_data["variants"][f"is_bonsai={bonsai},has_wire={wire},contents={content},facing={facing}"] = {
                    "model": model,
                    "y": rotation
                }

import json
print(json.dumps(json_data, indent=2))
