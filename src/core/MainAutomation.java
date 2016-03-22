package core;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;

import java.awt.Font;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import java.awt.event.KeyEvent;
import java.awt.event.InputEvent;

import javax.swing.SwingConstants;
import javax.swing.JPasswordField;
import javax.swing.JTextPane;
import javax.swing.JComboBox;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;

import javax.swing.JSeparator;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JCheckBox;

import java.awt.SystemColor;

import javax.swing.JTextArea;
import javax.swing.UIManager;

import core.Helpers.PrintStreamCapturer;
import core.Helpers.Timing;
import core.Helpers.Workbench;
import core.Helpers.XMLFile;

import java.awt.event.InputMethodListener;
import java.awt.event.InputMethodEvent;

import javax.swing.JScrollPane;

import com.google.common.base.Joiner;

public class MainAutomation {

	public static String reflectMethod = "";
	public static String massFile = "";
	public static String reportFile = "";
	public static String locatorsFile = "";
	public static String webDriver = "";
	public static String targetURL = "";
	public static String userLogin = "";
	public static String userPassword = "";
	public static String logonOnEatch = "false";

	public static String[] vScripts = new String[] { "", "Cadastrar Produto" }; // LABELS

	public static String[] vScriptsPath = new String[] { "",
			"produto.CadastrarProduto" }; // PATH

	public static String[] vDrivers = new String[] {
		"FireFox"
			// , "IE"
			// , "Chrome"
	};

	public static ReflectionController rc;

	private JFrame frmTmAutomao;
	private final Action actionExit = new SairAction();

	private String[] args;
	private JTextField txLogin;
	private JPasswordField pssPassword;

	private final Action actionStart = new StartAction();
	private final Action actionStop = new StopAction();
	private JTextArea txConsole;
	private JTextField txURL;
	private JComboBox<?> cbMethods;
	private JComboBox<?> cbDrivers;

	public static void sendMessage(String[] params) {
		System.out.println(Joiner.on(" | ").join(params));
	}

	public void reflectionExecution() {

		if (reflectMethod != "") {

			massFile = "Massa\\" + reflectMethod + ".csv";
			reportFile = "Relatorios\\" + reflectMethod + "["
					+ Timing.nowTime("yyyy.MM.dd.m.s") + "].htm";
			locatorsFile = "Locators\\" + reflectMethod + ".xml";

			args = new String[] { 
					reflectMethod, // [0] metodo a ser chamado
					massFile, // [1] arquivo de massa de dados
					reportFile, // [2] arquivo de relatorio
					locatorsFile, // [3] arquivo de Locators
					webDriver, // [3] webDriver a ser invocado (FireFox)
					targetURL, // [4] URL inicial
					userLogin,
					userPassword,
					logonOnEatch
			};

			try {

				rc = new ReflectionController();
				rc.exec(args);

			} catch (ClassNotFoundException | InstantiationException
					| IllegalAccessException | InvocationTargetException
					| IOException expt) {

				expt.printStackTrace();

			}
		}

	}

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		XMLFile xmlDefault = new XMLFile();
		xmlDefault.parse("default.xml");
		
		targetURL = xmlDefault.getElement("url").getAttribute("value");
		userLogin = xmlDefault.getElement("login").getAttribute("value");
		userPassword = xmlDefault.getElement("senha").getAttribute("value");

		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					MainAutomation window = new MainAutomation();
					window.frmTmAutomao.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainAutomation() {

		initialize();

		System.setOut(new PrintStreamCapturer(txConsole, System.out));

		txURL = new JTextField();
		txURL.setText(targetURL);
		txURL.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 12));
		txURL.setBounds(10, 47, 303, 24);
		frmTmAutomao.getContentPane().add(txURL);
		txURL.setColumns(10);

		JLabel lblSelecioneONavegador = new JLabel("Selecione o Navegador");
		lblSelecioneONavegador.setFont(new Font("Lucida Sans Unicode",
				Font.PLAIN, 13));
		lblSelecioneONavegador.setBounds(10, 243, 233, 20);
		frmTmAutomao.getContentPane().add(lblSelecioneONavegador);

	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmTmAutomao = new JFrame();
		frmTmAutomao.setResizable(false);
		frmTmAutomao.getContentPane().setBackground(Color.WHITE);
		frmTmAutomao.setTitle("T&M - AUTOMA\u00C7\u00C3O");
		frmTmAutomao.setBounds(100, 100, 331, 550);
		frmTmAutomao.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmTmAutomao.getContentPane().setLayout(null);

		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 327, 21);
		frmTmAutomao.getContentPane().add(menuBar);

		JMenu mnFile = new JMenu("Arquivo");
		menuBar.add(mnFile);

		JMenuItem mntmSair = new JMenuItem("Sair");
		mntmSair.setAction(actionExit);
		mntmSair.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W,
				InputEvent.CTRL_MASK));
		mnFile.add(mntmSair);

		JMenu mnHelp = new JMenu("Ajuda");
		menuBar.add(mnHelp);

		JMenuItem mntmAbout = new JMenuItem("Sobre");
		mnHelp.add(mntmAbout);

		/*
		 * panel.add( new JScrollPane( txMonitor ) ); MessageConsole mc = new
		 * MessageConsole(txMonitor); mc.redirectOut();
		 * mc.redirectErr(Color.RED, null); mc.setMessageLines(100);
		 * mc.redirectOut(null, System.out);
		 */

		JButton btnStart = new JButton("Iniciar");
		btnStart.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 11));
		btnStart.setAction(actionStart);
		btnStart.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
			}

		});

		btnStart.setBounds(203, 344, 110, 23);
		frmTmAutomao.getContentPane().add(btnStart);

		JSeparator separator = new JSeparator();
		separator.setBounds(52, 159, 233, -70);
		frmTmAutomao.getContentPane().add(separator);

		cbMethods = new JComboBox<Object>(vScripts);
		cbMethods.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				reflectMethod = vScriptsPath[cbMethods.getSelectedIndex()];
			}
		});

		cbMethods.setBounds(10, 214, 245, 20);
		frmTmAutomao.getContentPane().add(cbMethods);

		cbDrivers = new JComboBox<Object>(vDrivers);
		cbDrivers.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				webDriver = cbDrivers.getSelectedItem().toString();
				// System.out.println(webDriver);
			}
		});
		cbDrivers.setBounds(10, 263, 245, 20);
		frmTmAutomao.getContentPane().add(cbDrivers);

		JLabel label = new JLabel("Selecione o Script");
		label.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 13));
		label.setBounds(10, 191, 245, 20);
		frmTmAutomao.getContentPane().add(label);

		txLogin = new JTextField();
		txLogin.setBounds(10, 116, 86, 24);
		txLogin.setText(userLogin);
		frmTmAutomao.getContentPane().add(txLogin);
		txLogin.setColumns(10);

		JLabel lblLogin = new JLabel("Login");
		lblLogin.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 13));
		lblLogin.setBounds(10, 96, 41, 20);
		frmTmAutomao.getContentPane().add(lblLogin);

		pssPassword = new JPasswordField();
		pssPassword.setBounds(106, 116, 86, 24);
		pssPassword.setText(userPassword);
		frmTmAutomao.getContentPane().add(pssPassword);

		JLabel lblSenha = new JLabel("Senha");
		lblSenha.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 13));
		lblSenha.setHorizontalAlignment(SwingConstants.LEFT);
		lblSenha.setBounds(106, 98, 41, 14);
		frmTmAutomao.getContentPane().add(lblSenha);

		JLabel lblUrl = new JLabel("URL Inicial");
		lblUrl.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 13));
		lblUrl.setHorizontalAlignment(SwingConstants.LEFT);
		lblUrl.setBounds(10, 32, 99, 14);
		frmTmAutomao.getContentPane().add(lblUrl);

		JCheckBox ckbxLogonOnEatch = new JCheckBox(
				"Repetir logon a cada intera\u00E7\u00E3o");
		ckbxLogonOnEatch
				.setFont(new Font("Lucida Sans Unicode", Font.PLAIN, 13));
		ckbxLogonOnEatch.setBackground(Color.WHITE);
		ckbxLogonOnEatch.setBounds(8, 150, 247, 23);
		frmTmAutomao.getContentPane().add(ckbxLogonOnEatch);

		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(0, 378, 327, 18);
		frmTmAutomao.getContentPane().add(separator_1);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 380, 327, 141);
		frmTmAutomao.getContentPane().add(scrollPane);

		txConsole = new JTextArea();
		scrollPane.setViewportView(txConsole);
		txConsole.addInputMethodListener(new InputMethodListener() {
			public void caretPositionChanged(InputMethodEvent arg0) {
			}

			public void inputMethodTextChanged(InputMethodEvent arg0) {
				txConsole.setCaretPosition(txConsole.getText().length());
			}
		});
		txConsole.setBackground(UIManager
				.getColor("TextField.inactiveBackground"));
		txConsole.setLineWrap(true);
		txConsole.setFont(txConsole.getFont().deriveFont(
				txConsole.getFont().getStyle() & ~Font.BOLD & ~Font.ITALIC));
		txConsole.setEditable(false);

	}

	private class SairAction extends AbstractAction {

		/**
		 * 
		 */
		private static final long serialVersionUID = 8308418511299703968L;

		public SairAction() {
			putValue(NAME, "Sair");
			putValue(SHORT_DESCRIPTION, "Sair da ferramenta");
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			System.exit(0);
		}

	}

	private class StartAction extends AbstractAction {
		/**
		 * 
		 */
		private static final long serialVersionUID = 5153439754570628703L;

		public StartAction() {
			putValue(NAME, "Iniciar");
			putValue(SHORT_DESCRIPTION, "Inicia a execução da automação");
		}

		@Override
		public void actionPerformed(ActionEvent e) {

			if (reflectMethod.isEmpty()) {
				JOptionPane.showMessageDialog(null, "Selecione a Rotina Automática a ser executada");
			}
			else if (targetURL.isEmpty()){
				JOptionPane.showMessageDialog(null, "Selecione a URL a ser executada");
			}
			else if (userLogin.isEmpty()){
				JOptionPane.showMessageDialog(null, "Insira o Login a ser utilizado");
			} 
			else if (userPassword.isEmpty()){
				JOptionPane.showMessageDialog(null, "Insira a Senha a ser utilizada");
			} 
			else {
				webDriver = cbDrivers.getSelectedItem().toString();
				reflectMethod = vScriptsPath[cbMethods.getSelectedIndex()];
				reflectionExecution();
			}
			/*
			 * txMonitor.setText(txMonitor.getText()+
			 * "Mensagem de Texto do Console");
			 */

		}
	}

	private class StopAction extends AbstractAction {
		/**
			 * 
			 */
		private static final long serialVersionUID = 5153439754570628703L;

		public StopAction() {
			putValue(NAME, "Parar");
			putValue(SHORT_DESCRIPTION, "Interrompe a execução da automação");
		}

		@Override
		public void actionPerformed(ActionEvent e) {

		}
	}

	public JTextArea getTxConsole() {
		return txConsole;
	}

	public JComboBox<?> getCbMethods() {
		return cbMethods;
	}

	public JTextField getTxLogin() {
		return txLogin;
	}

	public JPasswordField getPssPassword() {
		return pssPassword;
	}

	public JTextField getTxURL() {
		return txURL;
	}

	public JComboBox<?> getCbDrivers() {
		return cbDrivers;
	}
}
