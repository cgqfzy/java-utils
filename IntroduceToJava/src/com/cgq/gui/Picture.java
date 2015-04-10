package com.cgq.gui;

/**
 * @comment
 * @author cgqfzy
 * @version 1.0
 * @date Apr 10, 2015 10:35:47 AM
 */
import java.awt.Color;
import java.awt.FileDialog;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

public final class Picture implements ActionListener {
	private BufferedImage image; // the rasterized image
	private JFrame frame; // on-screen view
	private String filename; // name of file
	private boolean isOriginUpperLeft = true; // location of origin
	private final int width, height; // width and height

	public Picture(int width, int height) {
		if (width < 0)
			throw new IllegalArgumentException("width must be nonnegative");
		if (height < 0)
			throw new IllegalArgumentException("height must be nonnegative");
		this.width = width;
		this.height = height;
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		// set to TYPE_INT_ARGB to support transparency
		filename = width + "-by-" + height;
	}

	/**
	 * Initializes a new picture that is a deep copy of picture
	 */
	public Picture(Picture picture) {
		width = picture.width();
		height = picture.height();
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		filename = picture.filename;
		for (int col = 0; col < width(); col++)
			for (int row = 0; row < height(); row++)
				image.setRGB(col, row, picture.get(col, row).getRGB());
	}

	/**
	 * Initializes a picture by reading in a .png, .gif, or .jpg from the given
	 * filename or URL name.
	 */
	public Picture(String filename) {
		this.filename = filename;
		try {
			// try to read from file in working directory
			File file = new File(filename);
			if (file.isFile()) {
				image = ImageIO.read(file);
			}

			// now try to read from file in same directory as this .class file
			else {
				URL url = getClass().getResource(filename);
				if (url == null) {
					url = new URL(filename);
				}
				image = ImageIO.read(url);
			}
			width = image.getWidth(null);
			height = image.getHeight(null);
		} catch (IOException e) {
			// e.printStackTrace();
			throw new RuntimeException("Could not open file: " + filename);
		}
	}

	/**
	 * Initializes a picture by reading in a .png, .gif, or .jpg from a File.
	 */
	public Picture(File file) {
		try {
			image = ImageIO.read(file);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Could not open file: " + file);
		}
		if (image == null) {
			throw new RuntimeException("Invalid image file: " + file);
		}
		width = image.getWidth(null);
		height = image.getHeight(null);
		filename = file.getName();
	}

	/**
	 * Returns a JLabel containing this picture, for embedding in a JPanel,
	 * JFrame or other GUI widget.
	 * 
	 * @return the <tt>JLabel</tt>
	 */
	public JLabel getJLabel() {
		if (image == null) {
			return null;
		} // no image available
		ImageIcon icon = new ImageIcon(image);
		return new JLabel(icon);
	}

	/**
	 * Sets the origin to be the upper left pixel. This is the default.
	 */
	public void setOriginUpperLeft() {
		isOriginUpperLeft = true;
	}

	/**
	 * Sets the origin to be the lower left pixel.
	 */
	public void setOriginLowerLeft() {
		isOriginUpperLeft = false;
	}

	/**
	 * Displays the picture in a window on the screen.
	 */
	public void show() {

		// create the GUI for viewing the image if needed
		if (frame == null) {
			frame = new JFrame();

			JMenuBar menuBar = new JMenuBar();
			JMenu menu = new JMenu("File");
			menuBar.add(menu);
			JMenuItem menuItem1 = new JMenuItem(" Save...   ");
			menuItem1.addActionListener(this);
			menuItem1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,
					Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
			menu.add(menuItem1);
			frame.setJMenuBar(menuBar);

			frame.setContentPane(getJLabel());
			// f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			frame.setTitle(filename);
			frame.setResizable(false);
			frame.pack();
			frame.setVisible(true);
		}

		// draw
		frame.repaint();
	}

	/**
	 * Returns the height of the picture.
	 * 
	 * @return the height of the picture (in pixels)
	 */
	public int height() {
		return height;
	}

	/**
	 * Returns the width of the picture.
	 * 
	 * @return the width of the picture (in pixels)
	 */
	public int width() {
		return width;
	}

	public Color get(int col, int row) {
		if (col < 0 || col >= width())
			throw new IndexOutOfBoundsException("col must be between 0 and "
					+ (width() - 1));
		if (row < 0 || row >= height())
			throw new IndexOutOfBoundsException("row must be between 0 and "
					+ (height() - 1));
		if (isOriginUpperLeft)
			return new Color(image.getRGB(col, row));
		else
			return new Color(image.getRGB(col, height - row - 1));
	}

	public void set(int col, int row, Color color) {
		if (col < 0 || col >= width())
			throw new IndexOutOfBoundsException("col must be between 0 and "
					+ (width() - 1));
		if (row < 0 || row >= height())
			throw new IndexOutOfBoundsException("row must be between 0 and "
					+ (height() - 1));
		if (color == null)
			throw new NullPointerException("can't set Color to null");
		if (isOriginUpperLeft)
			image.setRGB(col, row, color.getRGB());
		else
			image.setRGB(col, height - row - 1, color.getRGB());
	}

	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (obj == null)
			return false;
		if (obj.getClass() != this.getClass())
			return false;
		Picture that = (Picture) obj;
		if (this.width() != that.width())
			return false;
		if (this.height() != that.height())
			return false;
		for (int col = 0; col < width(); col++)
			for (int row = 0; row < height(); row++)
				if (!this.get(col, row).equals(that.get(col, row)))
					return false;
		return true;
	}

	/**
	 * Saves the picture to a file in a standard image format. The filetype must
	 * be .png or .jpg.
	 */
	public void save(String name) {
		save(new File(name));
	}

	/**
	 * Saves the picture to a file in a standard image format.
	 */
	public void save(File file) {
		this.filename = file.getName();
		if (frame != null) {
			frame.setTitle(filename);
		}
		String suffix = filename.substring(filename.lastIndexOf('.') + 1);
		suffix = suffix.toLowerCase();
		if (suffix.equals("jpg") || suffix.equals("png")) {
			try {
				ImageIO.write(image, suffix, file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("Error: filename must end in .jpg or .png");
		}
	}

	/**
	 * Opens a save dialog box when the user selects "Save As" from the menu.
	 */
	public void actionPerformed(ActionEvent e) {
		FileDialog chooser = new FileDialog(frame,
				"Use a .png or .jpg extension", FileDialog.SAVE);
		chooser.setVisible(true);
		if (chooser.getFile() != null) {
			save(chooser.getDirectory() + File.separator + chooser.getFile());
		}
	}

	/**
	 * Tests this <tt>Picture</tt> data type. Reads a picture specified by the
	 * command-line argument, and shows it in a window on the screen.
	 */
	public static void main(String[] args) {
		Picture picture = new Picture("http://bizhi.xue163.com/downpic/upload_xue163s/flashAll/20120618/1339988819qVhBSP.jpg");
		System.out.println("%d " + picture.width() +" -by- " + picture.height());
		picture.show();
	}

}
