package com.alkalus.core;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import gtPlusPlus.api.objects.Logger;
import gtPlusPlus.api.objects.data.AutoMap;
import gtPlusPlus.core.material.Particle;
import gtPlusPlus.core.util.Utils;
import gtPlusPlus.core.util.data.StringUtils;

public class MainClass {

	public static final String[] NAMES = new String[] {			
			"G",
			"u", "d", "c", "s", "t", "b",
			"e" + StringUtils.superscript("-"), "μ" + StringUtils.superscript("-"), "τ" + StringUtils.superscript("-"),
			"V" + StringUtils.subscript("e"), "V" + StringUtils.subscript("μ"), "V" + StringUtils.subscript("τ"),
			"g", "γ", "Z", "W", "H",
			"p", "n", "Λ" + StringUtils.superscript("0"), "Ω" + StringUtils.superscript("-"),
			"π" + StringUtils.superscript("0"), "η",
			"??"
	};

	public static final String[] ELEMENTS = new String[] { "H", "He", "Li", "Be", "B", "C", "N", "O", "F", "Ne", "Na",
			"Mg", "Al", "Si", "P", "S", "Cl", "Ar", "K", "Ca", "Sc", "Ti", "V", "Cr", "Mn", "Fe", "Co", "Ni", "Cu",
			"Zn", "Ga", "Ge", "As", "Se", "Br", "Kr", "Rb", "Sr", "Y", "Zr", "Nb", "Mo", "Tc", "Ru", "Rh", "Pd", "Ag",
			"Cd", "In", "Sn", "Sb", "Te", "I", "Xe", "Cs", "Ba", "La", "Ce", "Pr", "Nd", "Pm", "Sm", "Eu", "Gd", "Tb",
			"Dy", "Ho", "Er", "Tm", "Yb", "Lu", "Hf", "Ta", "W", "Re", "Os", "Ir", "Pt", "Au", "Hg", "Tl", "Pb", "Bi",
			"Po", "At", "Rn", "Fr", "Ra", "Ac", "Th", "Pa", "U", "Np", "Pu", "Am", "Cm", "Bk", "Cf", "Es", "Fm", "Md",
			"No", "Lr", "Rf", "Db", "Sg", "Bh", "Hs", "Mt", "Ds", "Rg", "Cn", "Nh", "Fl", "Mc", "Lv", "Ts", "Og" };

	public static final boolean GENERATE_IONS = true;
	public static final boolean GENERATE_PARTICLES = false;
	
	
	
	
	
	static public void main(String args[]) {
		Logger.INFO("Starting.");
		
		if (GENERATE_IONS) {
			generateIons(false);
		}
		if (GENERATE_PARTICLES) {
			generateParticles(false);			
		}
		
		
		
		
		
		
		
		
		
		

		Logger.INFO("Finished.");
	}

	public static void generateParticles(boolean useAllFonts) {
		execute(useAllFonts, 1, NAMES, null);
	}

	public static void generateIons(boolean useAllFonts) {
		execute(useAllFonts, 0, ELEMENTS, null);
	}

	static final String fonts[] = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
	@SuppressWarnings("deprecation")
	static final String fonts2[] = Toolkit.getDefaultToolkit().getFontList();
	static final AutoMap<String> aFonts = new AutoMap<String>();

	static {
		for (int i = 0; i < fonts.length; i++) {
			aFonts.put(fonts[i]);
		}
		for (int i = 0; i < fonts2.length; i++) {
			aFonts.put(fonts2[i]);
		}
	}

	private static void execute(boolean allFonts, int aMode, String[] aIconTextArray, String a3) {

		AutoMap<String> bFonts = new AutoMap<String>();		
		if (allFonts) {
			bFonts = aFonts; 
		}
		else {
			bFonts.put("Verdana");
		}
		
		
		
		
		
		Logger.INFO("Generating iconsets for " + bFonts.size() + " different fonts.");

		for (String FONT : bFonts)

			try {

				int width = 32, height = 32;
				
				String fullPath = "D:\\Coding\\Java\\Projects\\GTplusplus\\GeneratedIcons\\"+(aMode == 0 ? "Ion\\" : "Base\\");
				String filename;
				File aBaseicon = new File("D:\\Coding\\Java\\Projects\\GTplusplus\\GeneratedIcons\\" + "BASE.PNG");
				
				int yy = 0;
				
				if (aMode == 0) {
					for (String message : ELEMENTS) {
						
						//BufferedImage bi = ImageIO.read(aBaseicon);
						
						BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
						
						filename = "" + (yy++);
						Graphics2D ig2 = bi.createGraphics();
						String FONT_NAME = FONT;
						Font font = new Font(FONT_NAME, Font.BOLD, 18);
						ig2.setFont(font);

						FontMetrics fontMetrics = ig2.getFontMetrics();
						int stringWidth = fontMetrics.stringWidth(message);
						int stringHeight = fontMetrics.getAscent();

						Color aCol;
						short[] hsb = new short[3];					
						hsb = new short[] {128, 128, 128};						
						aCol = Color.getHSBColor(hsb[0], hsb[1], hsb[2]);
						ig2.setPaint(aCol);
						ig2.drawString(message, (width - stringWidth) / 2, height / 2 + stringHeight / 4);
						File dir = new File(fullPath + "\\");
						if (!dir.exists()) {
							dir.mkdir();
						}
						dir = new File(fullPath + (Utils.sanitizeString(FONT_NAME).toLowerCase()) + "\\");
						if (!dir.exists()) {
							dir.mkdir();
						}
						File F = new File(dir, filename + ".png");
						ImageIO.write(bi, "PNG", F);
					}
				}
				else {
					for (Particle p : Particle.aMap) {
						// BufferedImage bi = new BufferedImage(width, height,
						// BufferedImage.TYPE_INT_ARGB);

						BufferedImage bi = ImageIO.read(aBaseicon);

						int ameta = 0;
						String aPartName = Utils.sanitizeString(p.mParticleName.toLowerCase());
						for (String g : gtPlusPlus.core.item.chemistry.StandardBaseParticles.NameToMetaMap.keySet()) {
							if (g.toLowerCase().equals(aPartName)) {
								ameta = gtPlusPlus.core.item.chemistry.StandardBaseParticles.NameToMetaMap.get(aPartName);
							}
						}

						filename = "" + ameta;
						Graphics2D ig2 = bi.createGraphics();

						String FONT_NAME = FONT;

						Font font = new Font(FONT_NAME, Font.BOLD, 14);
						ig2.setFont(font);

						String message = NAMES[yy++];
						/*
						 * String message = p.mParticleName; message = (message.substring(0, 1));
						 */

						FontMetrics fontMetrics = ig2.getFontMetrics();
						int stringWidth = fontMetrics.stringWidth(message);
						int stringHeight = fontMetrics.getAscent();

						// ig2.setColor(Color.BLUE);
						// ig2.fill(new Ellipse2D.Float(8, 8, 48, 48));

						String aGroup = p.mParticleType.name().toLowerCase();
						Color aCol;
						float[] hsb = new float[3];

						if (p == Particle.GRAVITON) {
							// aColour = EnumChatFormatting.YELLOW;
							hsb = Color.RGBtoHSB(5, 5, 5, null);
						}

						else if (aGroup.toLowerCase().contains("quark")) {
							// aColour = EnumChatFormatting.LIGHT_PURPLE;
							hsb = Color.RGBtoHSB(157, 106, 190, null);
						} else if (aGroup.toLowerCase().contains("lepton")) {
							// aColour = EnumChatFormatting.GREEN;
							hsb = Color.RGBtoHSB(159, 233, 108, null);
						} else if (p == Particle.HIGGS_BOSON) {
							// aColour = EnumChatFormatting.YELLOW;
							hsb = Color.RGBtoHSB(238, 209, 85, null);
						} else if (aGroup.toLowerCase().contains("boson")) {
							// aColour = EnumChatFormatting.RED;
							hsb = Color.RGBtoHSB(246, 135, 106, null);
						} else if (aGroup.toLowerCase().contains("baryon")) {
							// aColour = EnumChatFormatting.BLUE;
							hsb = Color.RGBtoHSB(68, 103, 244, null);
						} else if (aGroup.toLowerCase().contains("meson")) {
							// aColour = EnumChatFormatting.WHITE;
							hsb = Color.RGBtoHSB(237, 237, 237, null);
						} else {
							// aColour = EnumChatFormatting.GRAY;
							hsb = Color.RGBtoHSB(50, 50, 50, null);
						}
						aCol = Color.getHSBColor(hsb[0], hsb[1], hsb[2]);

						ig2.setPaint(aCol);
						ig2.drawString(message, (width - stringWidth) / 2, height / 2 + stringHeight / 4);
						File dir = new File(fullPath + "\\");
						if (!dir.exists()) {
							dir.mkdir();
						}
						dir = new File(fullPath + (Utils.sanitizeString(FONT_NAME).toLowerCase()) + "\\");
						if (!dir.exists()) {
							dir.mkdir();
						}
						File F = new File(dir, filename + ".png");
						ImageIO.write(bi, "PNG", F);
					}
				}
				
				
				
				
				
				
				
				
				


			} catch (Throwable ie) {
				ie.printStackTrace();
			}
	}

}
