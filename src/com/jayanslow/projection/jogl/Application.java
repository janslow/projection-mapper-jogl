package com.jayanslow.projection.jogl;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import com.jayanslow.projection.texture.controllers.TextureController;
import com.jayanslow.projection.texture.editor.controller.StandardTextureEditorController;
import com.jayanslow.projection.texture.editor.controller.TextureEditorController;
import com.jayanslow.projection.world.controllers.WorldController;
import com.jayanslow.projection.world.editor.controller.StandardWorldEditorController;
import com.jayanslow.projection.world.editor.controller.WorldEditorController;

public class Application {

	public static void main(String[] args) {
		WorldController world = SampleWorld.makeSampleWorld();
		TextureController textures = SampleWorld.makeSampleTextures(world);

		final MasterVisualiser v = new MasterVisualiser(world, textures, 500, 500);
		v.setVisible(true);

		v.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		final ProjectorVisualiser p = new ProjectorVisualiser(world, textures, world.getProjector(0),
				RenderMode.TEXTURED);
		p.setVisible(true);

		final WorldEditorController worldEditor = new StandardWorldEditorController(world);
		worldEditor.editUniverse();

		final TextureEditorController textureEditor = new StandardTextureEditorController(world, textures);
		textureEditor.editMappings();
	}
}
