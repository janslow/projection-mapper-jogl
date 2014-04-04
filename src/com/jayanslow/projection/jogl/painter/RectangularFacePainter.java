package com.jayanslow.projection.jogl.painter;

import javax.media.opengl.GL2;
import javax.vecmath.Color4f;
import javax.vecmath.Vector2f;

import com.jayanslow.projection.world.models.RectangularFace;
import com.jayanslow.projection.world.models.RenderMode;

public class RectangularFacePainter extends AbstractFacePainter<RectangularFace> {
	public static boolean register(PainterFactory f) {
		return f.addPainter(new RectangularFacePainter(f));
	}

	public static void registerNested(PainterFactory f) {
		register(f);
	}

	public RectangularFacePainter(PainterFactory factory) {
		super(RectangularFace.class, factory, new Color4f(0, 1, 0, 1), new Color4f(0, 1, 0, 1));
	}

	@Override
	protected void paintFace(GL2 gl, RectangularFace t, RenderMode renderMode) {
		Vector2f dim = t.getDimensions();
		gl.glBegin(GL2.GL_QUADS);
		gl.glVertex3f(0, 0, 0);
		gl.glVertex3f(dim.x, 0, 0);
		gl.glVertex3f(dim.x, dim.y, 0);
		gl.glVertex3f(0, dim.y, 0);
		gl.glEnd();

		if (renderMode == RenderMode.WIREFRAME) {
			// Cross
			gl.glBegin(GL2.GL_LINES);
			gl.glVertex3f(0, 0, 0);
			gl.glVertex3f(dim.x, dim.y, 0);
			gl.glVertex3f(dim.x, 0, 0);
			gl.glVertex3f(0, dim.y, 0);
			gl.glEnd();

			gl.glBegin(GL2.GL_LINE_STRIP);
			gl.glVertex3f(dim.x / 2, dim.y / 2, 0);
			gl.glVertex3f(dim.x / 2, dim.y * 0.75f, 0);
			gl.glVertex3f(dim.x * 0.45f, dim.y * 0.7f, 0);
			gl.glVertex3f(dim.x / 2, dim.y * 0.75f, 0);
			gl.glVertex3f(dim.x * 0.55f, dim.y * 0.7f, 0);
			gl.glEnd();

		}
	}
}
