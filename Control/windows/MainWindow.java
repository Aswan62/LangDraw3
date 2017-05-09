package windows;

import itemList.DrawingList;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FileDialog;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.URL;
import java.util.Locale;
import java.util.Properties;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import javax.swing.filechooser.FileFilter;

import org.freehep.graphics2d.VectorGraphics;
import org.freehep.graphicsio.emf.EMFGraphics2D;
import org.freehep.graphicsio.ps.PSGraphics2D;
import org.jdesktop.swingx.JXFrame;

import render.DetectLocation;
import render.DrawWordAndMark;
import render.ImageCanvas;
import render.RenderQuality;
import textEditor.PaserThread;
import enums.LDXMLTags;

public class MainWindow extends JXFrame {


	/**
	 *
	 */
	private static final long serialVersionUID = -3350621937506526169L;
	private static MainWindow mainWindow;
	private static EditorWindow editorWindow;
	private static DrawingWindow drawingWindow;
	private JMenuBar menuBar;
	private JMenuItem mntmLoadXmlSource;
	private JMenuItem mntmSaveSource;
	private static File fileXmlSource;
	private static boolean xmlSourceSaved;
	private JMenuItem mntmSaveVectorImage;
	private JSeparator separator_1;
	private JSeparator separator_2;
	private JCheckBoxMenuItem chckbxmntmShowStyleEditor;
	private JSeparator separator_3;
	private JMenuItem mntmExit;
	private JMenu mnStyle;
	private SplashScreen splashScreen;

	final private static String fileName = "Update.property";
	final private static String fileNameLangDraw = "LangDraw.property";
	static private JCheckBoxMenuItem chckbxmntmShowImageEditor;
	private static boolean isDrawBorder;
	private JMenuItem mntmNewXmlsource;
	private String currentDir;

	Toolkit tk = Toolkit.getDefaultToolkit();
    int shotcutKey = tk.getMenuShortcutKeyMask();
    private JSeparator separator;
    private JMenuItem mntmImportStyleXml;
    private JMenuItem mntmImportStyleFrom;
	private JMenuItem mntmOpenStyleFolder;
	private JLabel lblTest;
	private StyleWindow styleWindow;
	private JSeparator separator_4;
	private JCheckBoxMenuItem checkmenuDrawBorder;

	FilenameFilter ffXML = new FilenameFilter() {
		@Override
		public boolean accept(File dir, String name) {
			if(name.toLowerCase().endsWith(".xml")) return true;
			else return false;
		}
	};
	private JMenu undoMenu;

	public static void setImageEditorShown(boolean isShown) {
		chckbxmntmShowImageEditor.setSelected(isShown);
	}

	public static MainWindow getMainWindow() {
		return mainWindow;
	}

	public static boolean isXmlSourceSaved() {
		return xmlSourceSaved;
	}

	public static void setXmlSourceSaved(boolean saved) {
		if(editorWindow != null) {

		}
		MainWindow.xmlSourceSaved = saved;
	}

	public static boolean isDrawBorder() {
		return isDrawBorder;
	}

	private boolean hasAlreadyRun() {
		ServerSocket socket = null;
		try {
			socket = new ServerSocket(38772);
		}catch(IOException e) {
			socket = null;
		}
		if(socket==null) {
			JOptionPane.showMessageDialog(null, "LangDraw is already running...");
			return true;
		}
		return false;
	}

	public MainWindow() {
		if(hasAlreadyRun()) return;

		setMinimumSize(new Dimension(600, 300));
        try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e1) {
			// TODO 自動生成された catch ブロック
			e1.printStackTrace();
		}

		String version = "";

		 Properties prop = new Properties();
		 try {
			prop.load(new FileInputStream("./" + fileName));
			version = prop.getProperty("Version");
		 } catch(Exception ex) {
			 ex.printStackTrace();
		 }

		setTitle("LangDraw 3  /  Ver. " + version);

		ImageIcon splash = null;
		try {
			URL imageIconUrl = getClass().getClassLoader().getResource("splash.png");
			splash = new ImageIcon(imageIconUrl);
			imageIconUrl = getClass().getClassLoader().getResource("LangDrawIcon.png");
			ImageIcon icon = new ImageIcon(imageIconUrl);
			setIconImage(icon.getImage());
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		splashScreen = new SplashScreen(splash, version);

		setLocation(new Point(0, 0));
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if(!MainWindow.isXmlSourceSaved()) {
					if(!confirmSaveReturnIsCancel()){
						systemExit();
					}
				}
				else {
					systemExit();
				}
			}
		});
		mainWindow = this;

		fileXmlSource = null;

		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setPreferredSize(new Dimension(600, 300));


		PaserThread thread = new PaserThread();

		editorWindow = new EditorWindow();
		editorWindow.setLocation(0, 0);
		drawingWindow = new DrawingWindow();
		drawingWindow.setLocation(0, 30);



		getRootPaneExt().getContentPane().add(editorWindow, BorderLayout.CENTER);


		styleWindow = new StyleWindow();
		menuBar = new JMenuBar();
		menuBar.setBackground(UIManager.getColor("control"));
		setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("File");
		mnFile.setBackground(UIManager.getColor("control"));
		mnFile.setMnemonic('F');
		menuBar.add(mnFile);

		mntmNewXmlsource = new JMenuItem("New XML file");
		mntmNewXmlsource.setBackground(UIManager.getColor("control"));
		mntmNewXmlsource.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				createNewXMLSource();
			}
		});
		mntmNewXmlsource.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, shotcutKey));
		mnFile.add(mntmNewXmlsource);

		mntmLoadXmlSource = new JMenuItem("Load XML file");
		mntmLoadXmlSource.setBackground(UIManager.getColor("control"));
		mntmLoadXmlSource.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				loadXMLFromFile();
			}
		});
		mntmLoadXmlSource.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, shotcutKey));
		mnFile.add(mntmLoadXmlSource);

		mntmSaveSource = new JMenuItem("Overwrite the XML source");
		mntmSaveSource.setBackground(UIManager.getColor("control"));
		mntmSaveSource.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveXMLToFile();
			}
		});

		separator_1 = new JSeparator();
		separator_1.setBackground(UIManager.getColor("control"));
		mnFile.add(separator_1);
		mntmSaveSource.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, shotcutKey));
		mnFile.add(mntmSaveSource);

		JMenuItem mntmSaveXmlSourceAs = new JMenuItem("Save the XML file as");
		mntmSaveXmlSourceAs.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveXMLToFileAs();
			}
		});
		mntmSaveXmlSourceAs.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, shotcutKey + KeyEvent.SHIFT_DOWN_MASK));
		mnFile.add(mntmSaveXmlSourceAs);

		separator_2 = new JSeparator();
		mnFile.add(separator_2);

		JMenuItem mntmSaveRasterImage = new JMenuItem("Save as a raster image");
		mntmSaveRasterImage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveImageToFile();
			}
		});
		mntmSaveRasterImage.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, shotcutKey + KeyEvent.SHIFT_DOWN_MASK));
		mnFile.add(mntmSaveRasterImage);

		mntmSaveVectorImage = new JMenuItem("Save as a vector image");
		mntmSaveVectorImage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveVectorToFile();
			}
		});
		mntmSaveVectorImage.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, shotcutKey + KeyEvent.SHIFT_DOWN_MASK));
		mnFile.add(mntmSaveVectorImage);

		separator_3 = new JSeparator();
		mnFile.add(separator_3);

		mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				systemExit();
			}
		});
		mntmExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, KeyEvent.ALT_DOWN_MASK));
		mnFile.add(mntmExit);

		JMenu mnView = new JMenu("View");
		mnView.setBackground(UIManager.getColor("control"));
		mnView.setMnemonic('V');
		menuBar.add(mnView);


		chckbxmntmShowStyleEditor = new JCheckBoxMenuItem("Show the Style window");
		chckbxmntmShowStyleEditor.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, shotcutKey + KeyEvent.ALT_DOWN_MASK));
		chckbxmntmShowStyleEditor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				styleWindow.setVisible(chckbxmntmShowStyleEditor.isSelected());
			}
		});
		if(styleWindow != null) chckbxmntmShowStyleEditor.setSelected(styleWindow.isVisible());
		else chckbxmntmShowStyleEditor.setSelected(false);
		mnView.add(chckbxmntmShowStyleEditor);

		chckbxmntmShowImageEditor = new JCheckBoxMenuItem("Show the Drawing window");
		chckbxmntmShowImageEditor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				drawingWindow.setVisible(chckbxmntmShowImageEditor.isSelected());
			}
		});
		chckbxmntmShowImageEditor.setSelected(true);
		chckbxmntmShowImageEditor.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, InputEvent.CTRL_MASK | InputEvent.ALT_MASK));
		mnView.add(chckbxmntmShowImageEditor);

		separator_4 = new JSeparator();
		mnView.add(separator_4);

		isDrawBorder = true;
		checkmenuDrawBorder = new JCheckBoxMenuItem("Show the border line in the Drawing window");
		checkmenuDrawBorder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				isDrawBorder = checkmenuDrawBorder.isSelected();
			}
		});
		checkmenuDrawBorder.setSelected(true);
		mnView.add(checkmenuDrawBorder);

		mnStyle = new JMenu("Managing Style");
		mnStyle.setBackground(UIManager.getColor("control"));
		mnStyle.addMenuListener(new MenuListener() {
			public void menuCanceled(MenuEvent e) {
			}
			public void menuDeselected(MenuEvent e) {
			}
			public void menuSelected(MenuEvent e) {
				mnStyle = setStyleList(mnStyle);
			}
		});
		mnStyle.setMnemonic('N');
		menuBar.add(mnStyle);

		mntmImportStyleXml = new JMenuItem("Import and save Style from XML file");
		mntmImportStyleXml.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mnStyle = extractSaveStyle(mnStyle);
			}
		});

		mntmImportStyleFrom = new JMenuItem("Import Style from XML file");
		mntmImportStyleFrom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mnStyle = importStyleFromXML(mnStyle);
			}
		});

		mntmOpenStyleFolder = new JMenuItem("Manage Style file");
		mntmOpenStyleFolder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				StyleMangeWindow window = new StyleMangeWindow();
				window.setLocationRelativeTo(mainWindow);
				window.setVisible(true);
			}
		});


		menuBar.add(Box.createHorizontalGlue());

		prop = new Properties();
		try {
			prop.load(new FileInputStream("./" + fileNameLangDraw));
			setWindowSizes(prop);

			currentDir = prop.getProperty(LDPropety.currentDirectory, System.getProperty("user.home"));
		} catch(Exception ex) {
			ex.printStackTrace();
		}

		splashScreen.hideSplash();

		this.setVisible(true);
		editorWindow.setVisible(true);
		drawingWindow.setVisible(true);
		styleWindow.setVisible(true);


		setXmlSourceSaved(true);
		createNewXMLSource();
		setXmlSourceSaved(true);

		thread.run();
	}

	private JMenu extractSaveStyle(JMenu menu) {
		String value = JOptionPane.showInputDialog(this, "Input style name.");
		try{
			File file = new File("./Styles/" + value + ".xml");

			String result = EditorWindow.getSourceText();
			int startIndex = result.indexOf("<" + LDXMLTags.Style + ">");
			int endIndex = result.indexOf("</" + LDXMLTags.Style + ">") + ("</" + LDXMLTags.Style + ">").length();
			if(startIndex != -1 && endIndex != -1)
			{
				result = result.substring(startIndex, endIndex);
				if (!file.exists()){
					FileWriter filewriter = new FileWriter(file);
					filewriter.write(result);
					filewriter.close();
					menu.add(new JMenuItem(value));
				}else{
					int num = JOptionPane.showConfirmDialog(this, "The filename file already exists. Do you want to overwrite it?", "Confirm.", JOptionPane.YES_NO_OPTION);
					if(num != JOptionPane.YES_OPTION) {
						return extractSaveStyle(menu);
					}
					else{
						FileWriter filewriter = new FileWriter(file);
						filewriter.write(result);
						filewriter.close();
					}
				}
			}
		}catch(IOException e){
			System.out.println(e);
		}
		return menu;
	}

	private JMenu setStyleList(JMenu menu) {
		menu.removeAll();

		mnStyle.add(mntmImportStyleFrom);
		mnStyle.add(mntmImportStyleXml);


		mnStyle.add(mntmOpenStyleFolder);

		separator = new JSeparator();
		mnStyle.add(separator);

		File targetDir = null;
		targetDir = new File("./Styles/");

		if (!targetDir.exists()) {
			targetDir.mkdir();
		}

		File[] fileList = targetDir.listFiles();
		for (int i = 0; i < fileList.length; i++) {
			String fileName = fileList[i].getName();
			if(fileName.endsWith(".xml")) {
				fileName = fileName.substring(0, fileName.length() - 4);
				JMenuItem menuItem = new JMenuItem(fileName);
				menuItem.addActionListener(new styleSelectAction());
				menu.add(menuItem);
			}
		}

		return menu;
	}


	private JMenu importStyleFromXML(JMenu menu) {

		FileDialog filechooser = new FileDialog(this, "File Open", FileDialog.LOAD);
		filechooser.setFile("*.xml");
		filechooser.setFilenameFilter(ffXML);
		filechooser.setDirectory(currentDir);

		try {
			getJMenuBar().setEnabled(false);
		    filechooser.setVisible(true);
		    if (filechooser.getDirectory() != null && filechooser.getFile() != null){
		    	currentDir = filechooser.getDirectory();
		    	String fileName = filechooser.getFile();
		    	if(!fileName.toLowerCase().endsWith(".xml")) fileName = fileName.concat(".xml");
		    	File file = new File(currentDir + fileName);

		    	String sourceText = EditorWindow.getSourceText();
		    	String newStyleText = setLoadStyle(file);
		    	String oldStyleText = extractStyleText(sourceText);

		    	if(oldStyleText != null && newStyleText != null) {
		    		sourceText = sourceText.replace(oldStyleText, newStyleText);
					EditorWindow.setSourceText(sourceText);
					EditorWindow.setSourceToBodyText(sourceText);

					menu = extractSaveStyle(menu);
			    }
		    }
		} finally {
			getJMenuBar().setEnabled(true);
		}

		return menu;
	}

	private void createNewXMLSource() {
		boolean isCanceled = false;
    	if(!isXmlSourceSaved()) isCanceled = confirmSaveReturnIsCancel();

    	if(!isCanceled) {
	    	fileXmlSource = null;
	    	setXmlSourceSaved(false);

	    	File file = new File("./default.xml");

	    	if(file.exists()) {
	    		setLoadXML(file);
	    	} else {
	    		EditorWindow.setSourceText("<LangDraw>\r\n  \r\n<!--Style Settings-->\r\n  <Style>\r\n  <Body Font-Family=\"Times New Roman\" Font-Size=\"30.0\" Font-Style=\"Plain\" Font-Color=\"#0\" Background-Color=\"#FFFFFF\" Font-Family-2Byte=\"MS Mincho\" Background-Color-Opacity=\"1.0\" />\r\n  <BR Line-Height=\"80\" />\r\n  <Span Font-Family=\"Times New Roman\" Font-Size=\"30.0\" Font-Style=\"Plain\" Font-Color=\"#0\" Border-Color=\"#0\" Border-Width=\"1.0\" Background-Color=\"#FFCCCC\" Font-Family-2Byte=\"MS Mincho\" Background-Color-Opacity=\"1.0\" Border-Color-Opacity=\"1.0\" Border-Form-Left=\"LINEAR\" Border-Form-Right=\"LINEAR\" />\r\n  <InMark Font-Family=\"Times New Roman\" Font-Size=\"30.0\" Font-Style=\"Plain\" Font-Color=\"#0\" Font-Family-2Byte=\"MS Mincho\" />\r\n  <Mark Font-Family=\"Times New Roman\" Font-Size=\"20.0\" Font-Style=\"Plain\" Font-Color=\"#0\" Margin=\"30.0\" Text-Align=\"Center\" Horizontal-Align=\"CENTER\" Font-Family-2Byte=\"MS Mincho\" Vertical-Align=\"SUB\" />\r\n  <Arrow Arrow-Color=\"#0\" Arrow-Width=\"1.0\" Radius=\"15.0\" Depth=\"100\" StartHead-Type=\"NONE\" EndHead-Type=\"OPEN\" StartHead-Size=\"1.0\" EndHead-Size=\"1.0\" />\r\n  <TextBox Font-Family=\"Times New Roman\" Font-Size=\"30.0\" Font-Style=\"Plain\" Font-Color=\"#0\" Fill-Background=\"true\" Background-Color=\"#FFFFFF\" Font-Family-2Byte=\"MS Mincho\" Padding=\"5.0\" Background-Color-Opacity=\"1.0\" />\r\n  <Span ID=\"Underline\" Text-Decoration=\"UNDERLINE\" Text-Decoration-Color=\"#0\" Text-Decoration-Color-Opacity=\"1.0\" />\r\n  <InMark Font-Size=\"18.0\" ID=\"Sub\" Vertical-Align=\"SUB\" />\r\n  <InMark Font-Size=\"18.0\" ID=\"Super\" Vertical-Align=\"SUPER\" />\r\n  <Span ID=\"Rect border\" Border-Color=\"#0\" Border-Color-Opacity=\"1.0\" Border-Width=\"1.0\" Border-Form-Left=\"LINEAR\" Border-Form-Right=\"LINEAR\" />\r\n  <Span ID=\"Eclipse\" Border-Color=\"#0\" Border-Color-Opacity=\"1.0\" Border-Width=\"1.0\" Border-Form-Left=\"CURVE2\" Border-Form-Right=\"CURVE2\" />\r\n</Style>\r\n  \r\n<!--TextBox Settings-->\r\n  <TextBoxes>\r\n\r\n  </TextBoxes>\r\n  \r\n<!--Arrow Settings-->\r\n  <Arrows>\r\n\r\n</Arrows>\r\n  \r\n<!--Sentence Settings-->\r\n  <Body></Body>\r\n</LangDraw>");
	    		EditorWindow.setBodyText("");
	    	}
    	}
	}

	private void loadXMLFromFile() {
		FileDialog filechooser = new FileDialog(this, "Please select XML file.", FileDialog.LOAD);
		filechooser.setFilenameFilter(ffXML);
		filechooser.setFile(".xml");

		try {
			getJMenuBar().setEnabled(false);
		    filechooser.setVisible(true);
		    if (filechooser.getFile() != null && filechooser.getDirectory() != null){
		    	currentDir = filechooser.getDirectory();
		    	File file = new File(currentDir + filechooser.getFile());
		    	setLoadXML(file);
		    }
		} finally {
			getJMenuBar().setEnabled(true);
		}
	}

	private void saveXMLToFile() {
		if(fileXmlSource != null)
			setSaveXML(fileXmlSource);
		else
			saveXMLToFileAs();
	}

	private void saveXMLToFileAs() {
		FileDialog filechooser = new FileDialog(this, "File save.", FileDialog.SAVE);
		filechooser.setFilenameFilter(ffXML);

		try {
			getJMenuBar().setEnabled(false);
			filechooser.setVisible(true);
		    if (filechooser.getFile() != null && filechooser.getDirectory() != null){
		    	File file = new File(filechooser.getDirectory() + filechooser.getFile());
		    	if(!file.getAbsolutePath().toLowerCase().endsWith(".xml")) {
		    		file = new File(file.getAbsolutePath().concat(".xml"));
		    	}

		    	if(fileExistCheck(file)) {
		    		currentDir = file.getAbsolutePath();
		    		fileXmlSource = file;
		    		setSaveXML(fileXmlSource);
		    	}
		    }
		    else {
		    	fileXmlSource = null;
		    }
		} finally {
			getJMenuBar().setEnabled(true);
		}
	}

	private void saveImageToFile() {
		FilenameFilter ffRaster = new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				if(name.toLowerCase().endsWith(".png")) return true;
				else return false;
			}
		};
		FileDialog filechooser = new FileDialog(this, "Save a raster image.", FileDialog.SAVE);
		filechooser.setFile("*.png");
		filechooser.setFilenameFilter(ffRaster);

		try {
			getJMenuBar().setEnabled(false);
		    filechooser.setVisible(true);
		    if (filechooser.getFile() != null && filechooser.getDirectory() != null){
		    	String fileName = filechooser.getFile();
		    	if(!fileName.toLowerCase().endsWith(".png")) fileName = fileName.concat(".png");
		    	File file = new File(filechooser.getDirectory() + fileName);

		    	if(fileExistCheck(file)) {
		    		currentDir = file.getAbsolutePath();
		    		setSaveImage(ImageCanvas.getSourceImage(), file, "png");
		    	}
		    }
		} finally {
				getJMenuBar().setEnabled(true);
		}
	}

	private void saveVectorToFile() {
		FilenameFilter ffRaster = new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				if(name.toLowerCase().endsWith(".eps")) return true;
				else return false;
			}
		};
		FileDialog filechooser = new FileDialog(this, "Save a vector image.", FileDialog.SAVE);
		filechooser.setFile("*.eps");
		filechooser.setFilenameFilter(ffRaster);

		try {
			getJMenuBar().setEnabled(false);
			filechooser.setVisible(true);
			 if (filechooser.getFile() != null && filechooser.getDirectory() != null){
				String fileName = filechooser.getFile();
				if(!fileName.toLowerCase().endsWith(".eps")) fileName = fileName.concat(".eps");
				File file = new File(filechooser.getDirectory() + fileName);
				if(fileExistCheck(file)) {
	    			currentDir = file.getAbsolutePath();
	    			outputEPS(file);
	    		}
		    }
	    } finally {
			getJMenuBar().setEnabled(true);
	    }
	}


	private String setLoadStyle(File file) {
		String result ="";
		BufferedReader b = null;
		try{
            FileReader f = new FileReader(file);
            b = new BufferedReader(f);
            String s;
            while((s = b.readLine())!=null){
            	result = result.concat(s + "\r\n");
            }

            return extractStyleText(result);
        }catch(Exception e){
        	e.printStackTrace();
        }finally {
        	try {
				b.close();
			} catch (IOException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
        }


		return null;
	}

	private void setLoadXML(File file) {
		String result ="";
		BufferedReader b = null;
		try{
			fileXmlSource = file;
            FileReader f = new FileReader(file);
            b = new BufferedReader(f);
            String s;
            while((s = b.readLine())!=null){
            	result = result.concat(s + "\r\n");
            }
        }catch(Exception e){
        	e.printStackTrace();
        	fileXmlSource = null;
        }finally {
        	try {
				b.close();
			} catch (IOException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}
        }

		EditorWindow.setSourceText(result);
		EditorWindow.setSourceToBodyText(result);
	}

	private void setSaveXML(File file) {
		String source = EditorWindow.sourceTextArea.getText();

		BufferedWriter bwriter = null;
		try {
			FileWriter writer = new FileWriter(file);
			bwriter = new BufferedWriter(writer);
			bwriter.write(source);
			MainWindow.setXmlSourceSaved(true);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(bwriter != null)
					bwriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void setSaveImage(BufferedImage img, File file, String type){

		try {
			ImageIO.write(img, type, file);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void outputEMF(File file){
		EMFGraphics2D vg = null;

		try{
			PaserThread.parseText(EditorWindow.sourceTextArea.getText());

			Rectangle2D.Float bounds = new Rectangle2D.Float();
        	bounds = DetectLocation.setWordFormatLocation(bounds, DrawingList.Words);
        	bounds = DetectLocation.setBorderLocation(bounds, DrawingList.Borders);
        	bounds = DetectLocation.setMarkFormatLocation(bounds, DrawingList.Words, DrawingList.Marks);
        	bounds = DetectLocation.setArrowLocation(bounds,  DrawingList.Arrows);
        	bounds = DetectLocation.setTextBoxLocation(bounds, DrawingList.TextBoxes);

        	vg = new EMFGraphics2D(file, new Dimension((int)bounds.getWidth(), (int)bounds.getHeight()));
        	vg.setDeviceIndependent(true);
        	vg.startExport();
			RenderQuality.setHighQuality();
			Graphics2D g = (Graphics2D) vg.create(0, 0, bounds.width, bounds.height);
			g.setRenderingHints(RenderQuality.getCurrentQuality());
			g = (Graphics2D) DrawWordAndMark.fillBackground(bounds, g);
			g = (Graphics2D) DrawWordAndMark.drawBorder(DrawingList.Borders, g);
			g = (Graphics2D) DrawWordAndMark.drawWords(DrawingList.Words, g);
			g = (Graphics2D) DrawWordAndMark.drawMarks(DrawingList.Marks, g);
			g = (Graphics2D) DrawWordAndMark.drawArrows(DrawingList.Arrows, g);
			g = (Graphics2D) DrawWordAndMark.drawTextBoxes(DrawingList.TextBoxes, g);
			vg.endExport();
		} catch(FileNotFoundException fe){
			fe.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(vg != null)
					vg.closeStream();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private static void outputEPS(File file){
		try{
			PaserThread.parseText(EditorWindow.sourceTextArea.getText());

			Rectangle2D.Float bounds = new Rectangle2D.Float();
        	bounds = DetectLocation.setWordFormatLocation(bounds, DrawingList.Words);
        	bounds = DetectLocation.setBorderLocation(bounds, DrawingList.Borders);
        	bounds = DetectLocation.setMarkFormatLocation(bounds, DrawingList.Words, DrawingList.Marks);
        	bounds = DetectLocation.setArrowLocation(bounds,  DrawingList.Arrows);
        	bounds = DetectLocation.setTextBoxLocation(bounds, DrawingList.TextBoxes);

			VectorGraphics vg = new PSGraphics2D(file, new Dimension((int)bounds.getWidth(), (int)bounds.getHeight()));
			vg.setDeviceIndependent(true);
			vg.startExport();
			RenderQuality.setHighQuality();
			Graphics2D g = (Graphics2D) vg.create(0, 0, bounds.width, bounds.height);
			g.setRenderingHints(RenderQuality.getCurrentQuality());
        	g = (VectorGraphics) DrawWordAndMark.fillBackground(bounds,  g);
        	g = (VectorGraphics) DrawWordAndMark.drawBorder(DrawingList.Borders, g);
        	g = (VectorGraphics) DrawWordAndMark.drawWords(DrawingList.Words, g);
        	g = (VectorGraphics) DrawWordAndMark.drawMarks(DrawingList.Marks, g);
        	g = (VectorGraphics) DrawWordAndMark.drawArrows(DrawingList.Arrows, g);
        	g = (VectorGraphics) DrawWordAndMark.drawTextBoxes(DrawingList.TextBoxes, g);
        	vg.endExport();
		}catch(FileNotFoundException fe){
			fe.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}


	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		new MainWindow();
	}

	private boolean confirmSaveReturnIsCancel() {

		int ret = JOptionPane.showConfirmDialog (null, "There have been some changes to the file. Do you want to save the file?", "Save option.", JOptionPane.YES_NO_CANCEL_OPTION);

		if(ret == JOptionPane.YES_OPTION) {
			 saveXMLToFile();
			 return false;
		}
		else if(ret == JOptionPane.NO_OPTION) {
			return false;
		}
		else {
			return true;
		}
	}

	private void setWindowSizes(Properties prop) {
		int mainX = Integer.parseInt(prop.getProperty(LDPropety.mainWindowX, "0"));
		int mainY = Integer.parseInt(prop.getProperty(LDPropety.mainWindowY, "0"));
		int mainWidth = Integer.parseInt(prop.getProperty(LDPropety.mainWindowWidth, "600"));
		int mainHeight = Integer.parseInt(prop.getProperty(LDPropety.mainWindowHeight, "300"));
		int mainWindowState = Integer.parseInt(prop.getProperty(LDPropety.mainWindowState, "0"));
		int editorX = Integer.parseInt(prop.getProperty(LDPropety.editorWindowX, "0"));
		int editorY = Integer.parseInt(prop.getProperty(LDPropety.editorWindowY, "0"));
		int editorWidth = Integer.parseInt(prop.getProperty(LDPropety.editorWindowWidth, "700"));
		int editorHeight = Integer.parseInt(prop.getProperty(LDPropety.editorWindowHeight, "300"));
		int drawingX = Integer.parseInt(prop.getProperty(LDPropety.drawingWindowX, "0"));
		int drawingY = Integer.parseInt(prop.getProperty(LDPropety.drawingWindowY, "300"));
		int drawingWidth = Integer.parseInt(prop.getProperty(LDPropety.drawingWindowWidth, "600"));
		int drawingHeight = Integer.parseInt(prop.getProperty(LDPropety.drawingWindowHeight, "300"));
		int fontSize = Integer.parseInt(prop.getProperty(LDPropety.currentFontSize, "12"));
		int styleX = Integer.parseInt(prop.getProperty(LDPropety.styleWindowX, "600"));
		int styleY = Integer.parseInt(prop.getProperty(LDPropety.styleWindowY, "0"));

		this.setLocation(mainX, mainY);
		this.setSize(mainWidth, mainHeight);
		this.setExtendedState(mainWindowState);
		editorWindow.setSize(editorWidth, editorHeight);
		editorWindow.setLocation(editorX, editorY);
		editorWindow.setFontSize(fontSize);
		drawingWindow.setSize(drawingWidth, drawingHeight);
		drawingWindow.setLocation(drawingX, drawingY);
		styleWindow.setLocation(styleX, styleY);
	}

	private void systemExit() {
		 Properties prop = new Properties();
		 FileOutputStream out = null;
		 try {
			prop.load(new FileInputStream("./" + fileNameLangDraw));
			if(this.getExtendedState() != JFrame.ICONIFIED) {
				prop.setProperty(LDPropety.mainWindowState, Integer.toString(this.getExtendedState()));
			} else {
				prop.setProperty(LDPropety.mainWindowState, Integer.toString(JFrame.NORMAL));
			}

			if(this.getExtendedState() != JFrame.NORMAL)
				this.setExtendedState(JFrame.NORMAL);


			Rectangle rect = new Rectangle(getLocation(), this.getSize());
			prop.setProperty(LDPropety.mainWindowX, Integer.toString(rect.x));
			prop.setProperty(LDPropety.mainWindowY, Integer.toString(rect.y));
			prop.setProperty(LDPropety.mainWindowWidth, Integer.toString(rect.width));
			prop.setProperty(LDPropety.mainWindowHeight, Integer.toString(rect.height));
			prop.setProperty(LDPropety.editorWindowX,  Integer.toString(editorWindow.getBounds().x));
			prop.setProperty(LDPropety.editorWindowY,  Integer.toString(editorWindow.getBounds().y));
			prop.setProperty(LDPropety.editorWindowWidth,  Integer.toString(editorWindow.getBounds().width));
			prop.setProperty(LDPropety.editorWindowHeight,  Integer.toString(editorWindow.getBounds().height));
			prop.setProperty(LDPropety.drawingWindowX,  Integer.toString(drawingWindow.getBounds().x));
			prop.setProperty(LDPropety.drawingWindowY,  Integer.toString(drawingWindow.getBounds().y));
			prop.setProperty(LDPropety.drawingWindowWidth,  Integer.toString(drawingWindow.getBounds().width));
			prop.setProperty(LDPropety.drawingWindowHeight,  Integer.toString(drawingWindow.getBounds().height));
			prop.setProperty(LDPropety.styleWindowX,  Integer.toString(styleWindow.getX()));
			prop.setProperty(LDPropety.styleWindowY,  Integer.toString(styleWindow.getY()));
			prop.setProperty(LDPropety.currentDirectory,  currentDir);
			prop.setProperty(LDPropety.currentFontSize,  Integer.toString(editorWindow.getFontSize()));
			out = new FileOutputStream("./" + fileNameLangDraw, false);
			prop.store(out, null);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(out != null)
					out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			System.exit(0);
		}
	}

	private void showMessage(String text) {
		JOptionPane.showConfirmDialog (null, text, "Message", JOptionPane.DEFAULT_OPTION);
	}

	private boolean fileExistCheck(File file) {
		if(file.exists()) {
			int result = JOptionPane.showConfirmDialog (null, "Are you sure want to overwrite?", "Overwrite confirmation.", JOptionPane.OK_CANCEL_OPTION);
			if(result == JOptionPane.OK_OPTION) {
				return true;
			} else {
				return false;
			}
		} else {
			return true;
		}
	}

	private String extractStyleText(String source) {
		String result = null;

		int startIndex = source.indexOf("<" + LDXMLTags.Style + ">");
        int endIndex = source.indexOf("</" + LDXMLTags.Style + ">") + ("</" + LDXMLTags.Style + ">").length();
        if(startIndex != -1 && endIndex != -1)
	    {
        	result = source.substring(startIndex, endIndex);
	    }

		return result;
	}
	private class SwingAction extends AbstractAction {
		public SwingAction() {
			putValue(NAME, "SwingAction");
			putValue(SHORT_DESCRIPTION, "Some short description");
		}
		public void actionPerformed(ActionEvent e) {
		}
	}
}

class EXTFilter extends FileFilter{

	private String extention;

	public EXTFilter(String ext) {
		extention = ext.toUpperCase();
	}

	public boolean accept(File f){
	if (f.isDirectory()){
		return true;
	}

	String ext = getExtension(f);
		if (ext != null){
			if (ext.equals(extention)){
				return true;
			} else {
				return false;
			}
		}

		return false;
	}

	public String getDescription(){
		return extention.concat(" File");
	}

	private String getExtension(File f){
		String ext = null;
		String filename = f.getName();
		int dotIndex = filename.lastIndexOf('.');

		if ((dotIndex > 0) && (dotIndex < filename.length() - 1)){
			ext = filename.substring(dotIndex + 1).toUpperCase();
		}

		return ext;
	}
}

class styleSelectAction extends AbstractAction {
	@Override
	public void actionPerformed(ActionEvent e) {
		String fileName = ((JMenuItem)e.getSource()).getText() + ".xml";
		String styleText = "";

		BufferedReader br = null;
		try {
			File file = new File("./Styles/" + fileName);

			br = new BufferedReader(new FileReader(file));
			String line;
			while ((line = br.readLine()) != null) {
				styleText = styleText.concat(line + "\r\n");
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException ex) {
			}
		}


		String source = EditorWindow.getSourceText();
		String oldText = extractStyleText(source);

		if(oldText != null) {
			source = source.replace(oldText, styleText);

			EditorWindow.setSourceText(source);
			EditorWindow.setSourceToBodyText(source);
		}
	}


	private String extractStyleText(String source) {
		String result = null;

		int startIndex = source.indexOf("<" + LDXMLTags.Style + ">");
        int endIndex = source.indexOf("</" + LDXMLTags.Style + ">") + ("</" + LDXMLTags.Style + ">").length();
        if(startIndex != -1 && endIndex != -1)
	    {
        	result = source.substring(startIndex, endIndex);
	    }

		return result;
	}
}

class LDPropety {
	final public static String autoResize = "AutoResize";
	final public static String mainWindowState = "MainWindowState";
	final public static String mainWindowX = "MainWindowX";
	final public static String mainWindowY = "MainWindowY";
	final public static String mainWindowWidth = "MainWindowWidth";
	final public static String mainWindowHeight = "MainWindowHeight";
	final public static String editorWindowX = "EditorWindowX";
	final public static String editorWindowY = "EditorWindowY";
	final public static String editorWindowWidth = "EditorWindowWidth";
	final public static String editorWindowHeight = "EditorWindowHeight";
	final public static String drawingWindowX = "DrawingWindowX";
	final public static String drawingWindowY = "DrawingWindowY";
	final public static String drawingWindowWidth = "DrawingWindowWidth";
	final public static String drawingWindowHeight = "DrawingWindowHeight";
	final public static String currentDirectory = "CurrentDirectory";
	final public static String currentFontSize = "CurrentFontSize";
	final public static String styleWindowX = "StyleWindowX";
	final public static String styleWindowY = "StyleWindowY";
}
