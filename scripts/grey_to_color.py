from PIL import Image

def rgb_to_greyscale_intensity(rgb):
    """
    Converts an RGB value to a greyscale intensity.
    
    Parameters:
        rgb (tuple): A tuple (R, G, B) representing the RGB value.
    
    Returns:
        int: Greyscale intensity (0-255).
    """
    r, g, b = rgb
    return int(0.2989 * r + 0.5870 * g + 0.1140 * b)

def get_color_from_colormap(greyscale_value, colormap_image):
    """
    Maps a greyscale value to a corresponding color in the colormap.
    
    Parameters:
        greyscale_value (int): Greyscale value (0-255).
        colormap_image (PIL.Image): The loaded colormap image.
    
    Returns:
        tuple: Corresponding (R, G, B) color from the colormap.
    """
    colormap_width = colormap_image.width
    
    # Calculate the position in the colormap corresponding to the greyscale value
    colormap_position = int((greyscale_value / 255.0) * (colormap_width - 1))
    
    # Get the color at the calculated position
    color = colormap_image.getpixel((colormap_position, 0))
    
    return color

def rgb_to_hex(rgb):
    """
    Converts an RGB tuple to a hex color code.
    
    Parameters:
        rgb (tuple): A tuple (R, G, B) representing the RGB value.
    
    Returns:
        str: Hex color code.
    """
    return '#{:02X}{:02X}{:02X}'.format(*rgb)

def main():
    # Load the colormap image
    colormap_path = "C:\\Users\\paccm\\Documents\\MCMods\\EricsLittleTrees\\src\\main\\resources\\assets\\ericslittletrees\\textures\\colormap\\foliage.png"
    colormap_image = Image.open(colormap_path).convert("RGB")
    
    # Example greyscale value to map
    greyscale_value = 135  # Example greyscale value
    
    # Map the greyscale value to its corresponding colormap color
    colormap_color = get_color_from_colormap(greyscale_value, colormap_image)
    colormap_color_hex = rgb_to_hex(colormap_color)
    
    print(f"Greyscale Value: {greyscale_value} -> Colormap Color (Hex): {colormap_color_hex}")

if __name__ == "__main__":
    main()
