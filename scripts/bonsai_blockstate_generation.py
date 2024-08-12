facing_directions = ["north", "east", "south", "west"]
contents_variants = ["empty", "oak_sapling", "spruce_sapling", "birch_sapling", "jungle_sapling", "acacia_sapling", "dark_oak_sapling", "cherry_sapling"]

json_data = {"variants": {}}

for facing in facing_directions:
    for content in contents_variants:
        rotation = 0
        if facing == "east":
            rotation = 90
        elif facing == "south":
            rotation = 180
        elif facing == "west":
            rotation = 270
        
        # Remove "_sapling" suffix if present
        #model_name = content.replace("_sapling", "")

        model_name = content
        
        # Construct model path based on the content
        model = f"ericslittletrees:block/bonsai_pot" if content == "empty" else f"ericslittletrees:block/pottedsapling/{model_name}_bonsai"
        
        json_data["variants"][f"contents={content},facing={facing}"] = {
            "model": model,
            "y": rotation
        }

import json
print(json.dumps(json_data, indent=2))
