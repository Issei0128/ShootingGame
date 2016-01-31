import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;


public class GameWindow extends JFrame{

	//バックバッファ
	BufferStrategy bufferStrategy;

	//グラフィック
	Graphics graphics;
	//画面サイズ
	int windowWidth,windowHeight;
	//画面比率
	float correctionWidth,correctionHeight;

	//コンストラクタ
	GameWindow(String windowName,int width,int height){

		//画面サイズを設定する
		windowWidth = width;
		windowHeight = height;

		//補正値
		correctionWidth = 1.0f;
		correctionHeight = 1.0f;

		if(height < width){
			correctionWidth = (float)width/(float)height;
		}else{
			correctionHeight = (float)height/(float)width;
		}

		//JFrameの設定
		getContentPane().setLayout(new FlowLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle(windowName);
		setSize(width, height);
		setResizable(false);
		setVisible(true);

		//BufferStrategyの設定をする
		createBufferStrategy(2);
		bufferStrategy = getBufferStrategy();

		graphics = bufferStrategy.getDrawGraphics();

		//アンチエイリアシングを設定する
		setAntiAliasing(true);
	}


	//アンチエイリアシングを設定する
	void setAntiAliasing(boolean flag){
		Graphics2D graphics2d = (Graphics2D)graphics;
		if(flag){
			graphics2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		}else{
			graphics2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_OFF);
		}
	}

	//バッファの再生成
	void reCreateBuffer(){
		//graphicsを開放
		graphics.dispose();
		//bufferStrategyを再生成する
		bufferStrategy = null;
		createBufferStrategy(2);
		bufferStrategy = getBufferStrategy();
		graphics = bufferStrategy.getDrawGraphics();
	}

	//フリップ処理
	void flip(){
		bufferStrategy.show();
	}

	//バックバッファをクリアする
	public void clear(int R,int G,int B){
		setColor(R,G,B);
		graphics.fillRect(0,0,windowWidth,windowHeight);
	}

	//描画色を設定する
	public void setColor(int R,int G,int B){
		if (R<=0){
			R=0;
		}
		if (R>255){
			R=255;
		}
		if (G<=0){
			G=0;
		}
		if (G>255){
			G=255;
		}
		if (B<=0){
			B=0;
		}
		if (B>255){
			B=255;
		}

		Color color=new Color(R,G,B);
		graphics.setColor(color);
	}

	public int toWindowWidthSize(float x){
		return (int)Math.rint(windowWidth * x);
	}

	public int toWindowHeightSize(float y){
		return (int)Math.rint(windowHeight * y);
	}


	//ラジアンに変換する
	public float toRadian(float angle){
		return (float)(angle * Math.PI / 180);
	}

	//角度に変更する
	public float toAngle(float radian){
		return (float)(radian * 180 / Math.PI);
	}


	//矩形を描画する
	public void fillRect(float x,float y,int Width,int Height){
		graphics.fillRect(	toWindowWidthSize(x),
							toWindowHeightSize(y),
							Width,Height);
	}
	
	//画面の補正を行わず矩形を描画する
	public void directfillRect(float x,float y,int Width,int Height){
		graphics.fillRect((int)x,(int)y,Width,Height);
	}
	
	//座標の中心を基準として矩形を描画する
	public void centeringfillRect(float x,float y,int Width,int Height){
		graphics.fillRect(	toWindowWidthSize(x)-(Width/2),
				toWindowHeightSize(y)-(Height/2),
				Width,Height);
	}

	//円を描画
	public void fillOval(float x,float y,int Width,int Height){
		graphics.fillOval(	toWindowWidthSize(x),
							toWindowHeightSize(y),
							Width,Height);

	}

	//座標の中心を基準として円を描画する
	public void centeringfillOval(float x,float y,int Width,int Height){
		graphics.fillOval(	toWindowWidthSize(x)-(Width/2),
				toWindowHeightSize(y)-(Height/2),
				Width,Height);
	}

	//点を描画
	public void point(float x,float y){
		graphics.drawLine(	toWindowWidthSize(x),
							toWindowHeightSize(y),
							toWindowWidthSize(x),
							toWindowHeightSize(y));
	}

	public void drawString(Object string,float x,float y){
		graphics.drawString(	String.valueOf(string),
								toWindowWidthSize(x),
								toWindowHeightSize(y));
	}

	public void drawString(Object string,int FontSize,float x,float y){

		Font oldFont = graphics.getFont();
		graphics.setFont(new Font(oldFont.getFontName(),oldFont.getStyle(),FontSize));

		graphics.drawString(	String.valueOf(string),
								toWindowWidthSize(x),
								toWindowHeightSize(y));
		graphics.setFont(oldFont);
	}
}