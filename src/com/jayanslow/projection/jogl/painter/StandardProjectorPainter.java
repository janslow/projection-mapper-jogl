package com.jayanslow.projection.jogl.painter;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.vecmath.Color4f;
import javax.vecmath.Vector3f;

import com.jayanslow.projection.jogl.utils.OpenGLUtils;
import com.jayanslow.projection.world.models.RenderMode;
import com.jayanslow.projection.world.models.StandardProjector;
import com.jogamp.opengl.util.gl2.GLUT;

public class StandardProjectorPainter extends AbstractRealObjectPainter<StandardProjector> {
	public static boolean register(PainterFactory f) {
		return f.addPainter(new StandardProjectorPainter(f));
	}

	public static void registerNested(PainterFactory f) {
		register(f);
	}

	public StandardProjectorPainter(PainterFactory factory) {
		super(StandardProjector.class, factory, new Color4f(1, 0, 0, 0), new Color4f(1, 0, 0, 0));
	}

	@Override
	protected void paintRealObject(GL2 gl, StandardProjector t, RenderMode renderMode) {
		GLUT glut = new GLUT();

		// Draw Projector Cuboid
		setWireframe(gl);
		gl.glPushMatrix();
		Vector3f dim = t.getDimensions();
		gl.glTranslatef(-dim.x / 2, -dim.y / 2, -dim.z);
		OpenGLUtils.drawRectangle(gl, dim.x, dim.y, dim.z);
		gl.glPopMatrix();

		float originalWidth = OpenGLUtils.getValuef(gl, GL.GL_LINE_WIDTH);
		// Draw Beam Direction
		gl.glLineWidth(3);
		setSolid(gl);
		float length = dim.z / 2;
		gl.glPushMatrix();
		gl.glBegin(GL2.GL_LINES);
		gl.glVertex3f(0, 0, 0);
		gl.glVertex3f(0, 0, length);
		gl.glEnd();
		gl.glTranslatef(0, 0, length);
		glut.glutSolidCone(length / 12, length / 4, 10, 10);
		gl.glPopMatrix();
		gl.glLineWidth(originalWidth);
	}
}
