package com.jayanslow.projection.jogl.painter;

import com.jayanslow.projection.world.models.FlatScreen;

public class FlatScreenPainter extends AbstractScreenPainter<FlatScreen> {
	public static boolean register(PainterFactory f) {
		return f.addPainter(new FlatScreenPainter(f));
	}

	public static void registerNested(PainterFactory f) {
		if (register(f))
			FacePainter.registerNested(f);
	}

	public FlatScreenPainter(PainterFactory factory) {
		super(FlatScreen.class, factory);
	}

}
