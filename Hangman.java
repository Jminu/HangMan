/*완성*/
/*타이틀 화면, 행맨 표정 추가*/
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Hangman extends JFrame implements ActionListener{
	String[] words = new String[201];
	int[] checked = new int [201]; // 나왔던 단어 체크하는 배열
	int word_length; // 단어의 길이
	int guessNum; // 맞추는 횟수 (10번만에 맞추는지 8번만에 맞추는지)
	int level; // 게임 난이도 
	char[] word1 = new char[12]; // 프로그램 안에서 돌아가는 char
	String[] slevel= {"Easy","Medium","Hard"}; // 난이도
	String[] word2 = new String[12]; // 화면에 출력할 String
	String check_word;
	double wins; //이긴 횟수
	double looses; //진 횟수
	double winningProsentige; //승률
	
	/*알파벳 버튼 26개 만든다*/
	JButton[] alpha_btn = new JButton[26];
	
	JButton begin = new JButton("BEGIN"); //시작 버튼
	
	/*난이도 버튼*/
	JButton easy = new JButton("EASY"); //난이도 easy버튼 
	JButton medium = new JButton("MEDIUM"); //난이도 medium버튼 
	JButton hard = new JButton("HARD"); //난이도 hard버튼
	
	JLabel text = new JLabel("Skill level: ", JLabel.LEFT); // 레벨 레이블로 띄우기
	JLabel title = new JLabel("Hang Man", JLabel.CENTER); //행맨 초기화면에 표시
	
	JPanel displayTOP = new JPanel(); //탑 패널 
	JPanel display1 = new JPanel(); //디스플레이1 패널
	JPanel display2 = new JPanel(); //디스플레이2 패널
	JPanel displayTitle = new JPanel(); //타이틀 출력할 패
	
	Font normalFont = new Font("Arial", Font.BOLD, 16);
	Font warningFont = new Font("Arial", Font.BOLD, 20);
	
	/*행맨 생성자*/
	public Hangman() {
		setTitle("2291048 진민우 행맨 게임");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(850,700);
		setVisible(true);
	}
	
	//모든 알파벳 버튼 활성화 or 비활성화 -> 함수로 처리함(코드 너무 길어져서)
	public void all_set_alpha_enable(boolean set) {
		for(int i = 0; i < 26; i++) {
			alpha_btn[i].setEnabled(set);	
		}
	}
	
	/*초기화*/
	public void init() {
		/*알파벳 버튼 초기화*/
		for(int i = 0; i < 26; i++) {
			alpha_btn[i] = new JButton(String.valueOf((char)(i + 65)));
		}
		/*알파벳 버튼 리스너 등록*/
		for(int i = 0; i < 26; i++) {
			alpha_btn[i].addActionListener(this);
		}
		
		/*시작 버튼과 난이도 버튼 리스너 등록*/
		begin.addActionListener(this);
		easy.addActionListener(this);
		medium.addActionListener(this);
		hard.addActionListener(this);
		
		/*레이아웃들*/
		GridLayout aaa = new GridLayout(4,0); // 전체 panel에 대한 layout 설정
		FlowLayout bbb = new FlowLayout();  // displayTOP에 대한 layout
        FlowLayout ccc = new FlowLayout(); // display2에 대한 layout
        GridLayout ddd = new GridLayout(2, 13); // display1에 대한 layout(행:2 열 13)
    
	    Container root = getContentPane(); // 컨테이너 타입의 객체 root	    
		root.setLayout(aaa); //grid
		root.setBackground(Color.white); //배경 흰색으로 설정
		
		displayTOP.add(begin); //begin버튼 top패널에 등록
		displayTOP.setLayout(bbb); //레이아웃 설정
		displayTOP.setBackground(Color.white); //배경 흰색
		root.add(displayTOP); // TOP을 컨테이너에 등록
		
		/*Hang Man 타이틀 중앙에 나오게끔*/
		title.setFont(new Font("Arial", Font.BOLD, 50));
		displayTitle.add(title, JLabel.CENTER);
		displayTitle.setBackground(Color.WHITE);
		root.add(displayTitle); //root contentPane에 타이틀 출력
		
		display1.setLayout(ddd); 
		display1.setBackground(Color.white);
		
		/*버튼들 배경화면 설정 오렌지색으로*/
		for(int i = 0; i < 26; i++) {
			alpha_btn[i].setBackground(Color.orange);
		}
		
		/*버튼들 디스플레이1 패널에 등록*/
		for(int i = 0 ;i < 26; i++) {
			display1.add(alpha_btn[i]);
		}
		root.add(display1); //root contentPane에 디스플레이1등록
		
		display2.setLayout(ccc);
		display2.setBackground(Color.white);
		display2.add(text);
		display2.add(easy);
		display2.add(medium);
		display2.add(hard);
		root.add(display2); //root contentPane에 디스플레이2등록
		
		setContentPane(root);
		
		/*알바벳 버튼들 비활성화(처음엔)*/
		all_set_alpha_enable(false);
		
		// begin이 눌리면 그때부터 활성화 (true)
		guessNum = 10; //처음 easy는 10번 기회
		easy.setEnabled(false);
		medium.setEnabled(true);
		hard.setEnabled(true);
		
		words[0] = "reminisce"; //200개 이상의 단어 구성, 철자 4개 이상 12개 이하
		words[1] = "dystopian";
		words[2] = "argument";
		words[3] = "parameter";
		words[4] = "language";
		words[5] = "program";
		words[6] = "reference";
		words[7] = "address";
		words[8] = "morning";
		words[9] = "network";
		words[10] = "data";
		words[11] = "process";
		words[12] = "paint";
		words[13] = "structure";
		words[14] = "algorithm";
		words[15] = "fight";
		words[16] = "bread";
		words[17] = "breakfast";
		words[18] = "dinner";
		words[19] = "elephant";
		words[20] = "start";
		words[21] = "remove";
		words[22] = "regardless";
		words[23] = "actor";
		words[24] = "smoke";
		words[25] = "weed";
		words[26] = "swim";
		words[27] = "patent";
		words[28] = "patient";
		words[29] = "baby";
		words[30] = "advertise";
		words[31] = "afford";
		words[32] = "affect";
		words[33] = "agenda";
		words[34] = "agency";
		words[35] = "agent";
		words[36] = "enable";
		words[37] = "enrich";
		words[38] = "automobile";
		words[39] = "arouse";
		words[40] = "amaze";
		words[41] = "abroad";
		words[42] = "await";
		words[43] = "approach";
		words[44] = "access";
		words[45] = "cease";
		words[46] = "concentrate";
		words[47] = "fund";
		words[48] = "fundamental";
		words[49] = "found";
		words[50] = "fragile";
		words[51] = "fraction";
		words[52] = "ingure";
		words[53] = "justify";
		words[54] = "reject";
		words[55] = "suppose";
		words[56] = "component";
		words[57] = "compound";
		words[58] = "opponent";
		words[59] = "possible";
		words[60] = "possess";
		words[61] = "route";
		words[62] = "routine";
		words[63] = "corrupt";
		words[64] = "bankrupt";
		words[65] = "observe";
		words[66] = "preserve";
		words[67] = "conserve";
		words[68] = "desert";
		words[69] = "exert";
		words[70] = "instant";
		words[71] = "obstacle";
		words[72] = "substance";
		words[73] = "estate";
		words[74] = "instance";
		words[75] = "constant";
		words[76] = "establish";
		words[77] = "destroy";
		words[78] = "strait";
		words[79] = "stress";
		words[80] = "prestige";
		words[81] = "contain";
		words[82] = "obtain";
		words[83] = "tailor";
		words[84] = "entire";
		words[85] = "integrate";
		words[86] = "attain";
		words[87] = "entire";
		words[88] = "entertain";
		words[89] = "detail";
		words[90] = "terminal";
		words[91] = "terminate";
		words[92] = "determine";
		words[93] = "term";
		words[94] = "terrible";
		words[95] = "distort";
		words[96] = "torment";
		words[97] = "tone";
		words[98] = "intonation";
		words[99] = "tune";
		words[100] = "contribute";
		words[101] = "distribute";
		words[102] = "trust";
		words[103] = "entrust";
		words[104] = "disturb";
		words[105] = "threat";
		words[106] = "evade";
		words[107] = "utensil";
		words[108] = "invade";
		words[109] = "vague";
		words[110] = "event";
		words[111] = "avenge";
		words[112] = "revenge";
		words[113] = "adventure";
		words[114] = "invent";
		words[115] = "convention";
		words[116] = "prevent";
		words[117] = "convenient";
		words[118] = "souvenir";
		words[119] = "converse";
		words[120] = "vertical";
		words[121] = "convert";
		words[122] = "convict";
		words[123] = "victory";
		words[124] = "divide";
		words[125] = "devise";
		words[126] = "widow";
		words[127] = "vigor";
		words[128] = "vegetable";
		words[129] = "vision";
		words[130] = "supervise";
		words[131] = "provide";
		words[132] = "vision";
		words[133] = "view";
		words[134] = "evidence";
		words[135] = "interview";
		words[136] = "review";
		words[137] = "survey";
		words[138] = "envy";
		words[139] = "survive";
		words[140] = "vocal";
		words[141] = "evoke";
		words[142] = "vocation";
		words[143] = "advocate";
		words[144] = "evolve";
		words[145] = "vowel";
		words[146] = "revolve";
		words[147] = "revolt";
		words[148] = "award";
		words[149] = "competent";
		words[150] = "effective";
		words[151] = "respect";
		words[152] = "benefit";
		words[153] = "literal";
		words[154] = "sensitive";
		words[155] = "practical";
		words[156] = "personal";
		words[157] = "alive";
		words[158] = "live";
		words[159] = "subjective";
		words[160] = "cloth";
		words[161] = "alternate";
		words[162] = "high";
		words[163] = "beside";
		words[164] = "adapt";
		words[165] = "adopt";
		words[166] = "expand";
		words[167] = "confirm";
		words[168] = "command";
		words[169] = "inquire";
		words[170] = "acquire";
		words[171] = "assess";
		words[172] = "vacation";
		words[173] = "affect";
		words[174] = "quite";
		words[175] = "principle";
		words[176] = "daily";
		words[177] = "elect";
		words[178] = "ignore";
		words[179] = "disclose";
		words[180] = "exclude";
		words[181] = "include";
		words[182] = "conclude";
		words[183] = "closet";
		words[184] = "accord";
		words[185] = "encourage";
		words[186] = "cordial";
		words[187] = "colony";
		words[188] = "excursion";
		words[189] = "cultivate";
		words[190] = "fable";
		words[191] = "fate";
		words[192] = "absent";
		words[193] = "faith";
		words[194] = "trust";
		words[195] = "fertile";
		words[196] = "refer";
		words[197] = "infer";
		words[198] = "prefer";
		words[199] = "force";
		words[200] = "cast";
		words[200] = "violence";
		
		for(int i=0;i<checked.length;i++) {
			checked[i]=0; // 아직 선택되지 않은 단어 (0)으로 초기화
		}
		
		for(int i=0;i<12;i++) {
			word1[i]=' '; // character // 프로그램 안에서 맞는지 틀린지
			word2[i]=" "; // string // 화면에 내보낼 때 
		}
				
		/* 필요한 변수들의 초기치 설정 */
		wins = 0;
		looses =0 ;
		winningProsentige = 0.0;
		
	}

	public void paint(Graphics screen) {
		super.paint(screen);
		Graphics2D screen2D = (Graphics2D) screen;
		screen2D.setFont(warningFont);
	
		screen2D.drawLine(70,60,130,60);
		screen2D.drawLine(70,60,70,80);
		screen2D.drawLine(130,60,130,170);
		screen2D.drawLine(60,170,160,170);
		
		if(level == 0) {
			switch(guessNum) {
			case 1:
				screen2D.setColor(Color.ORANGE);
				screen2D.fillOval(60, 80, 20, 20);
				screen2D.setColor(Color.BLACK);
				screen2D.drawLine(63, 90, 67, 90); //표정
				screen2D.drawLine(70, 90, 74, 90); //표정
				break;
			case 2:
				screen2D.setColor(Color.ORANGE);
				screen2D.fillOval(60, 80, 20, 20);
				screen2D.setColor(Color.BLACK);
				screen2D.drawLine(63, 90, 67, 90); //표정
				screen2D.drawLine(70, 90, 74, 90); //표정
				screen2D.drawLine(70,100,70,130); // 몸통
				break;
			case 3:
				screen2D.setColor(Color.ORANGE);
				screen2D.fillOval(60, 80, 20, 20);
				screen2D.setColor(Color.BLACK);
				screen2D.drawLine(63, 90, 67, 90); //표정
				screen2D.drawLine(70, 90, 74, 90); //표정
				screen2D.drawLine(70,100,70,130); // 몸통
				screen2D.drawLine(50,110,90,110); // 팔
				break;
			case 4:
				screen2D.setColor(Color.ORANGE);
				screen2D.fillOval(60, 80, 20, 20);
				screen2D.setColor(Color.BLACK);
				screen2D.drawLine(63, 90, 67, 90); //표정
				screen2D.drawLine(70, 90, 74, 90); //표정
				screen2D.drawLine(70,100,70,130); // 몸통
				screen2D.drawLine(50,110,90,110); // 팔
				break;
			case 5:
				screen2D.setColor(Color.ORANGE);
				screen2D.fillOval(60, 80, 20, 20);
				screen2D.setColor(Color.BLACK);
				screen2D.drawLine(63, 90, 67, 90); //표정
				screen2D.drawLine(70, 90, 74, 90); //표정
				screen2D.drawLine(70,100,70,130); // 몸통
				screen2D.drawLine(50,110,90,110); // 팔
				break;
			case 6:
				screen2D.setColor(Color.ORANGE);
				screen2D.fillOval(60, 80, 20, 20);
				screen2D.setColor(Color.BLACK);
				screen2D.drawLine(63, 90, 67, 90); //표정
				screen2D.drawLine(70, 90, 74, 90); //표정
				screen2D.drawLine(70,100,70,130); // 몸통
				screen2D.drawLine(50,110,90,110); // 팔
				break;
			case 7:
				screen2D.setColor(Color.ORANGE);
				screen2D.fillOval(60, 80, 20, 20);
				screen2D.setColor(Color.BLACK);
				screen2D.drawLine(63, 90, 67, 90); //표정
				screen2D.drawLine(70, 90, 74, 90); //표정
				screen2D.drawLine(70,100,70,130); // 몸통
				screen2D.drawLine(50,110,90,110); // 팔
				screen2D.drawLine(70,130,55,150); // 왼 다리
				break;
			case 8:
				screen2D.setColor(Color.ORANGE);
				screen2D.fillOval(60, 80, 20, 20);
				screen2D.setColor(Color.BLACK);
				screen2D.drawLine(63, 90, 67, 90); //표정
				screen2D.drawLine(70, 90, 74, 90); //표정
				screen2D.drawLine(70,100,70,130); // 몸통
				screen2D.drawLine(50,110,90,110); // 팔
				screen2D.drawLine(70,130,55,150); // 왼 다리
				break;
			case 9:
				screen2D.setColor(Color.ORANGE);
				screen2D.fillOval(60, 80, 20, 20);
				screen2D.setColor(Color.BLACK);
				screen2D.drawLine(63, 90, 67, 90); //표정
				screen2D.drawLine(70, 90, 74, 90); //표정
				screen2D.drawLine(70,100,70,130); // 몸통
				screen2D.drawLine(50,110,90,110); // 팔
				screen2D.drawLine(70,130,55,150); // 왼 다리
				screen2D.drawLine(70,130,85,150); // 오른 다리
				break;
			case 10:
				screen2D.setColor(Color.ORANGE);
				screen2D.fillOval(60, 80, 20, 20);
				screen2D.setColor(Color.BLACK);
				screen2D.drawLine(63, 90, 67, 90); //표정
				screen2D.drawLine(70, 90, 74, 90); //표정
				screen2D.drawLine(70,100,70,130); // 몸통
				screen2D.drawLine(50,110,90,110); // 팔
				screen2D.drawLine(70,130,55,150); // 왼 다리
				screen2D.drawLine(70,130,85,150); // 오른 다리
				break;
			}
		}
		
		if(level == 1) {
			switch(guessNum) {
			case 1:
				screen2D.setColor(Color.ORANGE);
				screen2D.fillOval(60, 80, 20, 20);
				screen2D.setColor(Color.BLACK);
				screen2D.drawLine(63, 90, 67, 90); //표정
				screen2D.drawLine(70, 90, 74, 90); //표정
				break;
			case 2:
				screen2D.setColor(Color.ORANGE);
				screen2D.fillOval(60, 80, 20, 20);
				screen2D.setColor(Color.BLACK);
				screen2D.drawLine(63, 90, 67, 90); //표정
				screen2D.drawLine(70, 90, 74, 90); //표정
				break;
			case 3:
				screen2D.setColor(Color.ORANGE);
				screen2D.fillOval(60, 80, 20, 20);
				screen2D.setColor(Color.BLACK);
				screen2D.drawLine(63, 90, 67, 90); //표정
				screen2D.drawLine(70, 90, 74, 90); //표정
				screen2D.drawLine(70,100,70,130); // 몸통
				
				break;
			case 4:
				screen2D.setColor(Color.ORANGE);
				screen2D.fillOval(60, 80, 20, 20);
				screen2D.setColor(Color.BLACK);
				screen2D.drawLine(63, 90, 67, 90); //표정
				screen2D.drawLine(70, 90, 74, 90); //표정
				screen2D.drawLine(70,100,70,130); // 몸통
				
				break;
			case 5:
				screen2D.setColor(Color.ORANGE);
				screen2D.fillOval(60, 80, 20, 20);
				screen2D.setColor(Color.BLACK);
				screen2D.drawLine(63, 90, 67, 90); //표정
				screen2D.drawLine(70, 90, 74, 90); //표정
				screen2D.drawLine(70,100,70,130); // 몸통
				screen2D.drawLine(50,110,90,110); // 팔
				break;
			case 6:
				screen2D.setColor(Color.ORANGE);
				screen2D.fillOval(60, 80, 20, 20);
				screen2D.setColor(Color.BLACK);
				screen2D.drawLine(63, 90, 67, 90); //표정
				screen2D.drawLine(70, 90, 74, 90); //표정
				screen2D.drawLine(70,100,70,130); // 몸통
				screen2D.drawLine(50,110,90,110); // 팔
				break;
			case 7:
				screen2D.setColor(Color.ORANGE);
				screen2D.fillOval(60, 80, 20, 20);
				screen2D.setColor(Color.BLACK);
				screen2D.drawLine(63, 90, 67, 90); //표정
				screen2D.drawLine(70, 90, 74, 90); //표정
				screen2D.drawLine(70,100,70,130); // 몸통
				screen2D.drawLine(50,110,90,110); // 팔
				screen2D.drawLine(70,130,55,150); // 왼 다리
				break;
			case 8:
				screen2D.setColor(Color.ORANGE);
				screen2D.fillOval(60, 80, 20, 20);
				screen2D.setColor(Color.BLACK);
				screen2D.drawLine(63, 90, 67, 90); //표정
				screen2D.drawLine(70, 90, 74, 90); //표정
				screen2D.drawLine(70,100,70,130); // 몸통
				screen2D.drawLine(50,110,90,110); // 팔
				screen2D.drawLine(70,130,55,150); // 왼 다리
				screen2D.drawLine(70,130,85,150); // 오른 다리
				break;
			}
		}
		
		if(level == 2) {
			switch(guessNum) {
			case 1:
				screen2D.setColor(Color.ORANGE);
				screen2D.fillOval(60, 80, 20, 20);
				screen2D.setColor(Color.BLACK);
				screen2D.drawLine(63, 90, 67, 90); //표정
				screen2D.drawLine(70, 90, 74, 90); //표정
				break;
			case 2:
				screen2D.setColor(Color.ORANGE);
				screen2D.fillOval(60, 80, 20, 20);
				screen2D.setColor(Color.BLACK);
				screen2D.drawLine(63, 90, 67, 90); //표정
				screen2D.drawLine(70, 90, 74, 90); //표정
				screen2D.drawLine(70,100,70,130); // 몸통
				break;
			case 3:
				screen2D.setColor(Color.ORANGE);
				screen2D.fillOval(60, 80, 20, 20);
				screen2D.setColor(Color.BLACK);
				screen2D.drawLine(63, 90, 67, 90); //표정
				screen2D.drawLine(70, 90, 74, 90); //표정
				screen2D.drawLine(70,100,70,130); // 몸통
				screen2D.drawLine(50,110,90,110); // 팔
				break;
			case 4:
				screen2D.setColor(Color.ORANGE);
				screen2D.fillOval(60, 80, 20, 20);
				screen2D.setColor(Color.BLACK);
				screen2D.drawLine(63, 90, 67, 90); //표정
				screen2D.drawLine(70, 90, 74, 90); //표정
				screen2D.drawLine(70,100,70,130); // 몸통
				screen2D.drawLine(50,110,90,110); // 팔
				break;
			case 5:
				screen2D.setColor(Color.ORANGE);
				screen2D.fillOval(60, 80, 20, 20);
				screen2D.setColor(Color.BLACK);
				screen2D.drawLine(63, 90, 67, 90); //표정
				screen2D.drawLine(70, 90, 74, 90); //표정
				screen2D.drawLine(70,100,70,130); // 몸통
				screen2D.drawLine(50,110,90,110); // 팔
				screen2D.drawLine(70,130,55,150); // 왼 다리
				break;
			case 6:
				screen2D.setColor(Color.ORANGE);
				screen2D.fillOval(60, 80, 20, 20);
				screen2D.setColor(Color.BLACK);
				screen2D.drawLine(63, 90, 67, 90); //표정
				screen2D.drawLine(70, 90, 74, 90); //표정
				screen2D.drawLine(70,100,70,130); // 몸통
				screen2D.drawLine(50,110,90,110); // 팔
				screen2D.drawLine(70,130,55,150); // 왼 다리
				screen2D.drawLine(70,130,85,150); // 오른 다리
				break;
			}
		}

		screen2D.setColor(Color.RED);
		screen2D.drawString(Integer.toString(guessNum)+" guesses left", 340, 240 );
		screen2D.setFont(normalFont);
		screen2D.setColor(Color.BLACK);
		screen2D.drawString("Current skill level: "+slevel[level], 300, 220 );
		screen2D.drawString("Wins ", 220, 200 );
		screen2D.drawString(Integer.toString((int)wins), 265, 200 );
		screen2D.drawString("Looses", 300, 200 );
		screen2D.drawString(Integer.toString((int)looses), 365, 200 );
		screen2D.drawString("WinningProsentige", 400, 200 );
		screen2D.drawString(Double.toString(winningProsentige)+"%", 555, 200 );
		
		/*단어 화면에 표시*/
		if(word_length >= 4 && word_length <= 12) {
			for(int i = 0; i < word_length; i++) {
				screen2D.setFont(normalFont);
				screen2D.setColor(Color.BLACK);
				screen2D.drawString(word2[i], 300 + 20 * i, 150 );
			}
		}
		//답이 맞은 단어에 대한 화면 표시
		//답이 틀린 단어에 대한 화면 표시   
		//시도한 횟수에 대하여 맞은 단어와 틀린 단어의 수 등을 표시 
	}
	public void wordSelect() {
		double sel_num = Math.random() * 201;// 0~200까지 단어중에 랜덤 선택
		int selection = (int) Math.floor(sel_num); // 0~200.x를 정수형으로 바꿈

		while(true) {  /* 이미 선택된 단어가 다시 선택되는 경우는 배제해야 함 */ // 나왔던 단어가 또 나오면 안됨
			if(checked[selection] == 0) { // 아직 뽑힌 단어가 아니라면 0
				checked[selection] = 1; //뽑혔다고 1로 표시하고
				break; //stop
			}
			else { //이미 뽑힌 단어라면 
				sel_num = Math.random() * 201; //다시뽑음
				selection = (int)Math.floor(sel_num);
			}
		}
		
		String sel_Word;
		
		if(words[selection] != null) { // 고른 단어가 null이 아니면
			sel_Word = words[selection].toLowerCase(); //뽑은 단어를 소문자로 변환
			word_length = sel_Word.length(); //뽑은 단어의 길이

			char[] temp = sel_Word.toCharArray();   // 뽑은 단어를 character 배열로 변환
			for(int index1 = 0; index1 < word_length; index1++) {
				 word1[index1] = temp[index1]; //뽑은 단어에서 알파벳 하나하나 넣는다 (word1은 내부에서 돌아감)
			}
			 for(int index2 = 0; index2 < word_length; index2++) {
				 word2[index2] = "_"; // .또는 _로 유저에게 단어의 철자 수를 알려줌 (word2는 화면에 출력할 것임)
			}
		}
	}
	
	//단어 리셋
	public void word_reset() { 
		for(int i=0;i<12;i++) {
			word2[i]="_"; //"_"로 초기화 후 
		}
		wordSelect(); //다시 단어 랜덤 선택
	}
	
	//스펠링 체크
	public void spell_check(char spell) {
		int check_key = 0;
		for(int i=0; i<word_length; i++) {  //12는 좋은 표현이 아님 -> word_length로 바꿈
			if(word1[i] != ' ') {
				if(word1[i] == spell) { //알파벳 맞췄다면
					word2[i] = "" + spell; //출력할 word2에 맞춘 알파벳 넣음
					check_key = 1;
					repaint(); //행맨 다시그림
				}
			}
		}
		
		if(check_key == 0) { // 끝까지 다 찾았는데 check_key가 0이면 특정 알파벳이 없음
			guessNum--; //기회 감소
			repaint(); //다시 그림
		}
		Adjust_display();
		repaint();
	}
	
	public void Adjust_display() {
		boolean correct = false; //단어 맞춘지 못맞춘지 확인하는 변수
		
		for(int i = 0; i < word_length; i++) {
			if(word2[i] != "_") {
				correct = true;
			}
			else {
				correct = false;
				break;
			}
		}
		
		if(correct) { //다 맞췄다면 
			all_set_alpha_enable(false); //모든 알파벳 버튼 비활성화
			begin.setEnabled(true); //begin버튼 활성화
			
			/*레벨에 따른 난이도 버튼 활성화*/
			if(level == 0) {
				medium.setEnabled(true);
				hard.setEnabled(true);
			} else if(level == 1) {
				easy.setEnabled(true);
				hard.setEnabled(true);
			} else if(level == 2) {
				easy.setEnabled(true);
				medium.setEnabled(true);
			}
			wins++; //단어추정 성공하면 이긴횟수 증가
			winningProsentige = (wins/(wins+looses))*100.0;
			repaint(); //다시 행맨그림
		}
		
		/*못 맞췄다면*/
		if(guessNum <= 0) {  // 단어 추정 실패 
			// 모든 알파벳 버튼 눌릴 수 없게 만듬
			all_set_alpha_enable(false);
			
			// 정답을 화면에 표시
			for(int i=0;i<12;i++){
				word2[i] = "" + word1[i];
			}
                        
			begin.setEnabled(true);	
			// level에 따른 버튼 활성화 작업 
			if(level == 0) {
				medium.setEnabled(true);
				hard.setEnabled(true);
			} else if(level == 1) {
				easy.setEnabled(true); 
				hard.setEnabled(true);
			} else if(level == 2) {
				easy.setEnabled(true);
				medium.setEnabled(true);
			}
			looses++; //패 증가 
			winningProsentige = (wins/(wins+looses))*100.0;			
			repaint();
		}

	}
	
	public void actionPerformed(ActionEvent event) {
		String typed = event.getActionCommand(); // 어떤 버튼을 눌렀는지 알려줌
		if(typed.equals("BEGIN")) { //시작버튼 눌린거면
			for(int i=0; i<12; i++){
				word1[i] = ' ';
				word2[i] = "_";
			}
				
			easy.setEnabled(false); //begin버튼 눌리면 난이도 버튼 비활성화
			medium.setEnabled(false);
			hard.setEnabled(false);

			if(level == 0) { //easy level은 기회 10번
				guessNum = 10;
			} 
			else if(level == 1) { //medium level은 기회 8번
				guessNum = 8;
			} 
			else if(level == 2) { //hard level은 기회 6번
				guessNum = 6;
			}
			displayTitle.setVisible(false); //Hang Man타이틀 표시 안되게
			repaint(); //다시 그리고
			
			//모든 알파벳 버튼 활성화(시작버튼 눌렸을 때)
			all_set_alpha_enable(true);
			begin.setEnabled(false); //시작버튼 비활성화
			word_reset(); //단어 리셋
		}
		
		if(typed.equals("A")) {
			alpha_btn[0].setEnabled(false);
			spell_check('a');
		}
		if(typed.equals("B")) {
			alpha_btn[1].setEnabled(false);
			spell_check('b');
		}
		if(typed.equals("C")) {
			alpha_btn[2].setEnabled(false);
			spell_check('c');
		}
		if(typed.equals("D")) {
			alpha_btn[3].setEnabled(false);
			spell_check('d');
		}
		if(typed.equals("E")) {
			alpha_btn[4].setEnabled(false);
			spell_check('e');
		}
		if(typed.equals("F")) {
			alpha_btn[5].setEnabled(false);
			spell_check('f');
		}
		if(typed.equals("G")) {
			alpha_btn[6].setEnabled(false);
			spell_check('g');
		}
		if(typed.equals("H")) {
			alpha_btn[7].setEnabled(false);
			spell_check('h');
		}
		if(typed.equals("I")) {
			alpha_btn[8].setEnabled(false);
			spell_check('i');
		}
		if(typed.equals("J")) {
			alpha_btn[9].setEnabled(false);
			spell_check('j');
		}
		if(typed.equals("K")) {
			alpha_btn[10].setEnabled(false);
			spell_check('k');
		}
		if(typed.equals("L")) {
			alpha_btn[11].setEnabled(false);
			spell_check('l');
		}
		if(typed.equals("M")) {
			alpha_btn[12].setEnabled(false);
			spell_check('m');
		}
		if(typed.equals("N")) {
			alpha_btn[13].setEnabled(false);
			spell_check('n');
		}
		if(typed.equals("O")) {
			alpha_btn[14].setEnabled(false);
			spell_check('o');
		}
		if(typed.equals("P")) {
			alpha_btn[15].setEnabled(false);
			spell_check('p');
		}
		if(typed.equals("Q")) {
			alpha_btn[16].setEnabled(false);
			spell_check('q');
		}
		if(typed.equals("R")) {
			alpha_btn[17].setEnabled(false);
			spell_check('r');
		}
		if(typed.equals("S")) {
			alpha_btn[18].setEnabled(false);
			spell_check('s');
		}
		if(typed.equals("T")) {
			alpha_btn[19].setEnabled(false);
			spell_check('t');
		}
		if(typed.equals("U")) {
			alpha_btn[20].setEnabled(false);
			spell_check('u');
		}
		if(typed.equals("V")) {
			alpha_btn[21].setEnabled(false);
			spell_check('v');
		}
		if(typed.equals("W")) {
			alpha_btn[22].setEnabled(false);
			spell_check('w');
		}
		if(typed.equals("X")) {
			alpha_btn[23].setEnabled(false);
			spell_check('x');
		}
		if(typed.equals("Y")) {
			alpha_btn[24].setEnabled(false);
			spell_check('y');
		}
		if(typed.equals("Z")) {
			alpha_btn[25].setEnabled(false);
			spell_check('z');
		}
		if(typed.equals("EASY")) { //easy mode 선택되면 
			easy.setEnabled(false);
			medium.setEnabled(true);
			hard.setEnabled(true);
			level = 0;
			guessNum = 10; //10번 기회
			displayTitle.setVisible(false); //Hang Man타이틀 표시 안되게
			repaint();
		}
		if(typed.equals("MEDIUM")) { //medium mode선택하면 
			easy.setEnabled(true);
			medium.setEnabled(false);
			hard.setEnabled(true);
			level = 1;
			guessNum = 8; //8번 기회
			displayTitle.setVisible(false); //Hang Man타이틀 표시 안되게
			repaint();
		}
		if(typed.equals("HARD")) { //hard mode 선택하면 
			easy.setEnabled(true);
			medium.setEnabled(true);
			hard.setEnabled(false);
			level = 2;
			guessNum = 6; //6번 기회
			displayTitle.setVisible(false); //Hang Man타이틀 표시 안되게
			repaint();
		}
	}
	
	public static void main(String [] args) {
		Hangman h = new Hangman();
		
		h.init();
	}
}
