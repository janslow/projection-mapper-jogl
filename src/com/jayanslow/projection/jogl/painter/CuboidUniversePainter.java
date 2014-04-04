package com.jayanslow.projection.jogl.painter;

import javax.media.opengl.GL2;
import javax.vecmath.Color4f;
import javax.vecmath.Vector3f;

import com.jayanslow.projection.jogl.RenderMode;
import com.jayanslow.projection.jogl.utils.OpenGLUtils;
import com.jayanslow.projection.world.models.CuboidUniverse;
import com.jayanslow.projection.world.models.RealObject;

public class CuboidUniversePainter extends AbstractSimplePainter<CuboidUniverse> {
	public static boolean register(PainterFactory f) {
		return f.addPainter(new CuboidUniversePainter(f));
	}

	public static void registerNested(PainterFactory f) {
		if (register(f))
			RealObjectPainter.registerNested(f);
	}

	public CuboidUniversePainter(PainterFactory factory) {
		super(CuboidUniverse.class, factory, new Color4f(0.3f, 0.8f, 0.8f, 1), null);
	}

	@Override
	public void paint(GL2 gl, CuboidUniverse t, RenderMode renderMode) {
		if (renderMode == RenderMode.SOLID) {
			gl.glEnable(GL2.GL_LIGHTING);
			gl.glEnable(GL2.GL_LIGHT0);

			float SHINE_ALL_DIRECTIONS = 1;
			float[] lightPos = { 0, 0, 0, SHINE_ALL_DIRECTIONS };
			float[] lightColorAmbient = { 0.2f, 0.2f, 0.2f, 1f };
			float[] lightColorSpecular = { 0.8f, 0.8f, 0.8f, 1f };

			// Set light parameters.
			gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_POSITION, lightPos, 0);
			gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_AMBIENT, lightColorAmbient, 0);
			gl.glLightfv(GL2.GL_LIGHT1, GL2.GL_SPECULAR, lightColorSpecular, 0);

			// Enable lighting in GL.
			gl.glEnable(GL2.GL_LIGHT1);
			gl.glEnable(GL2.GL_LIGHTING);
		}

		super.paint(gl, t, renderMode);
	}

	@Override
	protected void paintObject(GL2 gl, CuboidUniverse t, RenderMode renderMode) {
		Vector3f dim = t.getDimensions();

		OpenGLUtils.drawRectangle(gl, dim.x, dim.y, dim.z);

		for (RealObject o : t.getChildren())
			getFactory().paint(gl, RealObject.class, o, renderMode);
	}

	@Override
	protected void setSolid(GL2 gl) {
		setWireframe(gl);
	}

}
