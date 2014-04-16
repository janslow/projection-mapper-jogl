package com.jayanslow.projection.jogl;

public class RenderMode {
	public enum FaceMode {
		INVISIBLE, BLACK, SHADED_WHITE, TEXTURED;
	}

	public enum Visibility {
		VISIBLE, INVISIBLE;

		public static Visibility from(boolean stroke) {
			return stroke ? VISIBLE : INVISIBLE;
		}
	}

	public static RenderMode	TEXTURED	= setUpTextured(), SOLID = setUpSolid(), WIREFRAME = setUpWireframe(),
			OUTLINE = setUpOutline();

	private static RenderMode setUpOutline() {
		return new RenderMode(true, true, true, true, FaceMode.BLACK);
	}

	private static RenderMode setUpSolid() {
		return new RenderMode(false, true, true, true, FaceMode.SHADED_WHITE);
	}

	private static RenderMode setUpTextured() {
		return new RenderMode(false, false, false, false, FaceMode.TEXTURED);
	}

	private static RenderMode setUpWireframe() {
		return new RenderMode(true, true, true, true, FaceMode.INVISIBLE);
	}

	public final Visibility	stroke, origin, universe, projector;
	public final FaceMode	faces;

	public RenderMode(boolean stroke, boolean origin, boolean universe, boolean projector, FaceMode faces) {
		this(Visibility.from(stroke), Visibility.from(origin), Visibility.from(universe), Visibility.from(projector),
				faces);
	}

	public RenderMode(Visibility stroke, Visibility origin, Visibility universe, Visibility projector, FaceMode faces) {
		super();
		this.stroke = stroke;
		this.origin = origin;
		this.universe = universe;
		this.projector = projector;
		this.faces = faces;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RenderMode other = (RenderMode) obj;
		if (faces != other.faces)
			return false;
		if (origin != other.origin)
			return false;
		if (projector != other.projector)
			return false;
		if (stroke != other.stroke)
			return false;
		if (universe != other.universe)
			return false;
		return true;
	}

	public FaceMode getFaceMode() {
		if (getIsFaceVisible())
			return faces;
		else
			return FaceMode.INVISIBLE;
	}

	public boolean getIsFaceVisible() {
		return faces != null && faces != FaceMode.INVISIBLE;
	}

	public boolean getIsOriginVisible() {
		return origin == Visibility.VISIBLE;
	}

	public boolean getIsProjectorVisible() {
		return projector == Visibility.VISIBLE;
	}

	public boolean getIsStrokeVisible() {
		return stroke == Visibility.VISIBLE;
	}

	public boolean getIsUniverseVisible() {
		return universe == Visibility.VISIBLE;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((faces == null) ? 0 : faces.hashCode());
		result = prime * result + ((origin == null) ? 0 : origin.hashCode());
		result = prime * result + ((projector == null) ? 0 : projector.hashCode());
		result = prime * result + ((stroke == null) ? 0 : stroke.hashCode());
		result = prime * result + ((universe == null) ? 0 : universe.hashCode());
		return result;
	}

	public boolean isOutline() {
		return equals(OUTLINE);
	}
}
