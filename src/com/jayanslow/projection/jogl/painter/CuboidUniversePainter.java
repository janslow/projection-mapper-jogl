package com.jayanslow.projection.jogl.painter;

import javax.media.opengl.GL2;
import javax.vecmath.Color4f;
import javax.vecmath.Vector3f;

import com.jayanslow.projection.jogl.utils.OpenGLUtils;
import com.jayanslow.projection.world.models.CuboidUniverse;
import com.jayanslow.projection.world.models.RealObject;
import com.jayanslow.projection.world.models.RenderMode;

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
