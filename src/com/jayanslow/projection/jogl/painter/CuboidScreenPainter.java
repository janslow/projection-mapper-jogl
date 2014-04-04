package com.jayanslow.projection.jogl.painter;

import com.jayanslow.projection.world.models.CuboidScreen;

public class CuboidScreenPainter extends AbstractScreenPainter<CuboidScreen> {
	public static boolean register(PainterFactory f) {
		return f.addPainter(new CuboidScreenPainter(f));
	}

	public static void registerNested(PainterFactory f) {
		if (register(f))
			FacePainter.registerNested(f);
	}

	public CuboidScreenPainter(PainterFactory factory) {
		super(CuboidScreen.class, factory);
	}

}
