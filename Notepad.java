package notepad_clone;


import java.awt.FileDialog;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.desktop.OpenFilesEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class Notepad {
      JFrame frame;                      // stands for the main window of Notepad application
	  JTextArea textArea;                // stand for the typing or displaying text.
	  
	  JMenuBar menuBar;                  // stand for the menus of the application (file, language, format, command prompt)
	  JMenu fileMenu, LanguageMenu, formatMenu,commandPromptMenu;
	  
//	  File menu items
	  JMenuItem itemNew,itemNewWindow,itemOpen, itemSave, itemSaveAs, itemExit;
	  
//	  format menu items
	  JMenuItem itemWordWrap, itemFont, itemFontSize;
	  
//	  command prompt item
	  JMenuItem itemCMD;
	    String openPath = null;
	    String openFilename = null;
	    
	    boolean wrap = false;
	    
	 Font   arial,timesNewRoman,consolas;
	 
	 String fontstyle = "Arial";
      
	public Notepad() {
		createFrame();
		createTextArea();
		createScrollBars();
		createMenuBar();
		createFileMenuItems();
		createFormatItems();
		createCommandPromptItem();
		createLanguageItems();
		
	}
	
	public void createFrame() {
		
		frame = new JFrame("Notepad");
		
		frame.setSize(700,900);
		Image icon = Toolkit.getDefaultToolkit().getImage("C:\\Users\\LENOVO\\OneDrive\\Desktop\\Notepad.JFIF");
		
		frame.setIconImage(icon);
		frame.setVisible(true);
		
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	public void createTextArea() 
	{
		textArea = new JTextArea();
		
		frame.add(textArea);	
	}
	
	public void createScrollBars()
	{
		JScrollPane scroll = new JScrollPane(textArea,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		frame.add(scroll);
	}
	
	public void createMenuBar() 
	{
		menuBar = new JMenuBar();
		
		frame.setJMenuBar(menuBar);
		
		fileMenu = new JMenu("File");
		
		menuBar.add(fileMenu);
		
		LanguageMenu = new JMenu("Language");
		
		menuBar.add(LanguageMenu);
		
		formatMenu = new JMenu("Format");
		
		menuBar.add(formatMenu);
		
		commandPromptMenu = new JMenu("Command Prompt");
		
		menuBar.add(commandPromptMenu);
			
	}
//	action listener interface. used for receiving action events the class that is interested in processing action.
	public void createFileMenuItems() 
	{
		itemNew = new JMenuItem("New");             // new clear the text area.
		
		itemNew.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				textArea.setText("");
				frame.setTitle("Untitled Notepad");
				
				openFilename= null;
				openPath= null;
			}
		});	
		fileMenu.add(itemNew);
		
		itemNewWindow = new JMenuItem("New Window");
		
		  itemNewWindow.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Notepad n1 = new Notepad();
				n1.frame.setTitle("Untitled");
				
			}});
		
		fileMenu.add(itemNewWindow);      // new window opens a new notepad window
		
		itemOpen = new JMenuItem("Open");   // open opens a file using file dialog and load is content into the text area.
		
		itemOpen.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) throws NullPointerException {
				FileDialog fd = new FileDialog(frame,"Open",FileDialog.LOAD);
				
				fd.setVisible(true);
				
			   String path = fd.getDirectory();
				String filename = fd.getFile();
				
				if(filename!=null) {
					frame.setTitle(filename);
					
					 openFilename = filename;
					 openPath = path;
				}
				 BufferedReader br = null ;
				try {
				  br = new BufferedReader(new FileReader(path+filename));
				
				String sentence  = br.readLine();
				textArea.setText("");
				
				while (sentence!=null) {
					
					textArea.append(sentence + "\n");
					
					sentence = br.readLine();
				}
				
			 }    catch(FileNotFoundException e1) {
				
				System.out.println("File not found");
				
				} catch(IOException e1) {
					
					System.out.println("Data could not be read");
				}
				catch(NullPointerException e2) {
					
				}
				finally {
					
					try {
						br.close();
					} catch (IOException e1) {
					System.out.println("File could not be close");	
					}
					catch(NullPointerException e2) {
						
					}
					finally {
						
						try {
							br.close();
						}catch (Exception e2) {
							System.out.println("file could not found");
						}
					}
				}
				
			}
			
		});
		
		fileMenu.add(itemOpen);
		
		itemSave = new JMenuItem("Save");     // save works as content of the text area to the  current file.
		
		itemSave.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if (openFilename!=null && openPath!=null) {
					
					 writeDataToFile(openFilename , openPath);
				}
				else {
					FileDialog fd = new FileDialog(frame,"Save As",FileDialog.SAVE);
					
					fd.setVisible(true);
					
					String path = fd.getDirectory();
					
					String filename = fd.getFile();
					
					if (path!=null&filename!=null) {
						
						writeDataToFile(filename,path);
						
						openFilename = filename;
						openPath = path;
						
						frame.setTitle(openFilename);
					}
				}
				
			}
		});
		
		
		fileMenu.add(itemSave);
		
		itemSaveAs = new JMenuItem("Save As");    // save as work as open a dialog to specify filename and save location for the file
		
		itemSaveAs.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				FileDialog fd = new FileDialog(frame,"Save As", FileDialog.SAVE);
				
				fd.setVisible(true);
				
				String path = fd.getDirectory();
				
				String filename = fd.getFile();
				
				if (path!=null&filename!=null) {
					
					writeDataToFile(filename,path);
					openFilename = filename;
					openPath = path;
					
					frame.setTitle(openFilename);
					
				}
					}
				});
			
		fileMenu.add(itemSaveAs);
		
		itemExit = new JMenuItem("Exit");      // closes the application.
		
		itemExit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
			frame.dispose();
				
			}
		});
		
		fileMenu.add(itemExit);
		
	}
	
	public void createLanguageItems()
	{
		JMenuItem itemjava = new JMenuItem("Java");
		itemjava.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				setLanguage("Java");
				
			}
		});
		LanguageMenu.add(itemjava);
		
        JMenuItem itemC = new JMenuItem("C");
       itemC.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				setLanguage("C");
				
			}
		});
		LanguageMenu.add(itemC);
		

        JMenuItem itemHTML = new JMenuItem("HTML");
       itemHTML.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				setLanguage("HTML");
				
			}
		});
		LanguageMenu.add(itemHTML);
		
		
		
	}
	
	public void setLanguage(String lang) 
	{
		 BufferedReader br = null ;
			try {
			  br = new BufferedReader(new FileReader("F:\\Filehandle\\"+lang+ "Format.txt"));
			
			String sentence  = br.readLine();
			textArea.setText("");
			
			while (sentence!=null) {
				
				textArea.append(sentence + "\n");
				
				sentence = br.readLine();
			}
			
		 }    catch(FileNotFoundException e1) {
			
			System.out.println("File not found");
			
			} catch(IOException e1) {
				
				System.out.println("Data could not be read");
			}
			catch(NullPointerException e2) {
				
			}
			finally {
				
				try {
					br.close();
				} catch (IOException e1) {
				System.out.println("File could not be close");	
				}
				catch(NullPointerException e2) {
					
				}
				finally {
					
					try {
						br.close();
					}catch (Exception e2) {
						System.out.println("file could not found");
					}
				}
			}
			
		}
		
	public void createFormatItems()
	{
		itemWordWrap = new JMenuItem("Word Wrap Off");
		formatMenu.add(itemWordWrap);
		
		itemWordWrap.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
			
				if(wrap==false) {
					
					textArea.setLineWrap(true);
					
					textArea.setWrapStyleWord(true);
					
					wrap = true;
					
					itemWordWrap.setText("Word Wrap on");
				}
				else
				{
					textArea.setLineWrap(false);
					
					textArea.setWrapStyleWord(false);
					
					wrap= false;
					
					itemWordWrap.setText("Word Wrap Off");
				}
				
			}
		});
	
		
		itemFont = new JMenu("Font");
		
		formatMenu.add(itemFont);
		
		JMenuItem itemArial = new JMenuItem("Arial");
		itemArial.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				setFontStyle("Arial");
				
			}
		});
		JMenuItem itemTimesNewsRoman = new JMenuItem("TimesNewsRoman");
		itemTimesNewsRoman.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				setFontStyle("TimesNewsRoman");
				
			}
		});
		JMenuItem  itemConsolas = new  JMenuItem("Consolas");
		itemConsolas.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				setFontStyle("Consolas");
				
			}
		});
		
		itemFont.add(itemArial);
		itemFont.add(itemTimesNewsRoman);
		itemFont.add(itemConsolas);
		
		itemFontSize = new JMenu("Font Size");
		
		formatMenu.add(itemFontSize);
		

		JMenuItem size10 = new JMenuItem("10");
		size10.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				setFontSize(10);
				
			}
		});
		itemFontSize.add(size10);
		
		JMenuItem size15 = new JMenuItem("15");
        size15.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				setFontSize(15);
				
			}
		});
		
		itemFontSize.add(size15);
		
		JMenuItem size20 = new JMenuItem("20");
        size20.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				setFontSize(20);
				
			}
		});
		itemFontSize.add(size20);
		
		JMenuItem size25 = new JMenuItem("25");
        size25.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				setFontSize(25);
				
			}
		});
		itemFontSize.add(size25);
		
	
		
	}
	
	public void setFontSize(int size)
	{
		arial = new Font ("Arial",Font.PLAIN,size);
		timesNewRoman = new Font("TimesNewsRoman",Font.PLAIN,size);
		consolas = new Font("Consolas",Font.PLAIN,size);
		
		setFontStyle(fontstyle);
				
	}
	
	public void setFontStyle(String font)
	{
		fontstyle = font;
		switch(font) 
		{
		case "Arial":
			textArea.setFont(arial);
			break;
		case "TimesNewsRoman":
			textArea.setFont(timesNewRoman);
			break;
		case "Consolas":
			textArea.setFont(consolas);
			break;	
			
			default:
				break;	
		}
		}
	
	public void createCommandPromptItem()
	{
		itemCMD = new JMenuItem("Open CMD");
		
		itemCMD.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
			
				try {
					if(openPath!=null) {
						Runtime.getRuntime().exec(new String[] {"cmd","/C","start"},null,new File(openPath));
//						/k is use for to create command prompt open.
					}
					else {
						Runtime.getRuntime().exec(new String[] {"cmd","/C","start"},null,null);
					}
					
				} catch (Exception e2) {
					System.out.println("could not launch cmd");
				}
				
			}
		});
		
		commandPromptMenu.add(itemCMD);
	}
	
	public void writeDataToFile(String filename,String path) 
	{
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(path+filename));
			
			String text = textArea.getText();
			
			bw.write(text);
			
		} catch(IOException e1) {
			
			System.out.println("Data cannot be written");
		}
		finally {
			
			try {
				bw.close();
			}catch(IOException e1) {
				System.out.println("File cannot be closed");
			}
			catch(NullPointerException e2) {
				System.out.println("File not found to close");
			}
		}
	}
	
}
