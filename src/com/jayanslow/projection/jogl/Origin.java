package com.jayanslow.projection.jogl;

import javax.vecmath.Color4f;

public class Origin {
	public static Color4f	DEFAULT_COLOR_X	= new Color4f(1, 0, 0, 1), DEFAULT_COLOR_Y = new Color4f(0, 0, 1, 1),
			DEFAULT_COLOR_Z = new Color4f(0, 1, 0, 1);

	private final Color4f	colorX, colorY, colorZ;
	private final float		sizeX, sizeY, sizeZ;

	public Origin(Color4f colorX, Color4f colorY, Color4f colorZ, float size) {
		this(colorX, colorY, colorZ, size, size, size);
	}

	public Origin(Color4f colorX, Color4f colorY, Color4f colorZ, float sizeX, float sizeY, float sizeZ) {
		super();
		this.colorX = colorX;
		this.colorY = colorY;
		this.colorZ = colorZ;
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		this.sizeZ = sizeZ;
	}

	public Origin(float size) {
		this(DEFAULT_COLOR_X, DEFAULT_COLOR_Y, DEFAULT_COLOR_Z, size);
	}

	public Color4f getColorX() {
		return new Color4f(colorX);
	}

	public Color4f getColorY() {
		return new Color4f(colorY);
	}

	public Color4f getColorZ() {
		return new Color4f(colorZ);
	}

	public float getSizeX() {
		return sizeX;
	}

	public float getSizeY() {
		return sizeY;
	}

	public float getSizeZ() {
		return sizeZ;
	}
}
