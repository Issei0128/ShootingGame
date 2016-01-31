import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/*
 * プレイヤークラス
 */
public class Player extends GameObject implements KeyListener{

	float vx,vy;
	//初期HP
	public static final int firstHP = 100;
	private boolean up,down,left,right,space,slow;

	private float normal_vx,normal_vy;
	private float slow_vx,slow_vy;

	public Player(float x,float y,float vx,float vy,GameWindow graphics){
		super(x,y,false);

		normal_vx = vx / graphics.correctionWidth;
		normal_vy = vy / graphics.correctionHeight;
		slow_vx = normal_vx/2.5f;
		slow_vy = normal_vy/2.5f;
		hp = firstHP;
		up = down = left = right = space = false;

	}

	public void process(){

		if(GameManager.playerBullets.size()< 100 ){

			if(space == true){
				if(slow == true){

					GameManager.playerBullets.add(new PlayerBullet(x,y,
							0.02f*(float)Math.cos(GameMain.graphics.toRadian(85)),
							0.02f*(float)-(Math.sin(GameMain.graphics.toRadian(85))))
							);
					GameManager.playerBullets.add(new PlayerBullet(x,y,
							0.02f*(float)Math.cos(GameMain.graphics.toRadian(88)),
							0.02f*(float)-(Math.sin(GameMain.graphics.toRadian(88))))
						);
					GameManager.playerBullets.add(new PlayerBullet(x,y,
							0.02f*(float)Math.cos(GameMain.graphics.toRadian(90)),
							0.02f*(float)-(Math.sin(GameMain.graphics.toRadian(90))))
						);
					GameManager.playerBullets.add(new PlayerBullet(x,y,
							0.02f*(float)Math.cos(GameMain.graphics.toRadian(93)),
							0.02f*(float)-(Math.sin(GameMain.graphics.toRadian(93))))
						);
					GameManager.playerBullets.add(new PlayerBullet(x,y,
							0.02f*(float)Math.cos(GameMain.graphics.toRadian(96)),
							0.02f*(float)-(Math.sin(GameMain.graphics.toRadian(96))))
						);

				}else{
					GameManager.playerBullets.add(new PlayerBullet(x,y,
							0.02f*(float)Math.cos(GameMain.graphics.toRadian(70)),
							0.02f*(float)-(Math.sin(GameMain.graphics.toRadian(70))))
							);
					GameManager.playerBullets.add(new PlayerBullet(x,y,
							0.02f*(float)Math.cos(GameMain.graphics.toRadian(80)),
							0.02f*(float)-(Math.sin(GameMain.graphics.toRadian(80))))
							);
					GameManager.playerBullets.add(new PlayerBullet(x,y,
							0.02f*(float)Math.cos(GameMain.graphics.toRadian(90)),
							0.02f*(float)-(Math.sin(GameMain.graphics.toRadian(90))))
						);
					GameManager.playerBullets.add(new PlayerBullet(x,y,
							0.02f*(float)Math.cos(GameMain.graphics.toRadian(100)),
							0.02f*(float)-(Math.sin(GameMain.graphics.toRadian(100))))
						);
					GameManager.playerBullets.add(new PlayerBullet(x,y,
							0.02f*(float)Math.cos(GameMain.graphics.toRadian(110)),
							0.02f*(float)-(Math.sin(GameMain.graphics.toRadian(110))))
						);
				}
			}
		}

		//速度を決定する
		if(slow == true){
			vx = slow_vx;
			vy = slow_vy;
		}else{
			vx = normal_vx;
			vy = normal_vy;

		}


		if(left || right || up || down){
			//角度を計算する
			int radian = 0;
			//移動処理
			if(left == true){
				radian =180;
			}
			if(right == true){
				radian = 0;
			}
			if(down == true){
				radian = 270;
			}
			if(up == true){
				radian = 90;
			}
			//右上
			if(up == true && right == true){
				radian = 45;
			}
			//左上
			if(up == true && left == true){
				radian = 135;
			}
			//右下
			if(down == true && right == true){
				radian = 315;
			}
			//左下
			if(down == true && left == true){
				radian = 225;
			}

			x += vx*(float)Math.cos(GameMain.graphics.toRadian(radian));
			y += vy*(float)-(Math.sin(GameMain.graphics.toRadian(radian)));
		}

		//画面外に進まないようにする
		if(x<0.0f){
			x = 0.0f;
		}else if(x>0.99f){
			x=0.99f;
		}
		if(y<0.05f){
			y = 0.05f;
		}else if(y>0.99f){
			y=0.99f;
		}


	};
	
	public void draw(){
		//プレイヤーを表示する
		GameMain.graphics.setColor(128,128,128);
		// 四角を表示
		GameMain.graphics.directfillRect(
				GameMain.graphics.toWindowWidthSize(x)-15,
				GameMain.graphics.toWindowHeightSize(y)-15,
				30, 30);

		GameMain.graphics.setColor(255,0,0);
		//中心を表示する
		GameMain.graphics.point(x, y);

		//HPを表示する
		GameMain.graphics.drawString(hp, 15, x - 0.01f, y + 0.05f);
	};

	//初期HPを返す
	public int ReturnFirstHP(){
		return firstHP;
	}


	public void keyTyped(KeyEvent e) {

	}

	//キーを押したときの処理
	public void keyPressed(KeyEvent e) {
		switch(e.getKeyCode()){
		case KeyEvent.VK_LEFT:
			left = true;
			break;
		case KeyEvent.VK_A:
			left = true;
			break;
		case KeyEvent.VK_RIGHT:
			right = true;
			break;
		case KeyEvent.VK_D:
			right = true;
			break;
		case KeyEvent.VK_UP:
			up = true;
			break;
		case KeyEvent.VK_W:
			up = true;
			break;
		case KeyEvent.VK_DOWN:
			down = true;
			break;
		case KeyEvent.VK_S:
			down = true;
			break;

		case KeyEvent.VK_SPACE:
			space = true;
			break;

		case KeyEvent.VK_SHIFT:
			slow = true;
			break;
		}
	}

	//キーを離したときの処理
	public void keyReleased(KeyEvent e) {
		switch(e.getKeyCode()){
		case KeyEvent.VK_LEFT:
			left = false;
			break;
		case KeyEvent.VK_A:
			left = false;
			break;
		case KeyEvent.VK_RIGHT:
			right = false;
			break;
		case KeyEvent.VK_D:
			right = false;
			break;
		case KeyEvent.VK_UP:
			up = false;
			break;
		case KeyEvent.VK_W:
			up = false;
			break;
		case KeyEvent.VK_DOWN:
			down = false;
			break;
		case KeyEvent.VK_S:
			down = false;
			break;

		case KeyEvent.VK_SPACE:
			space = false;
			break;
			
		case KeyEvent.VK_SHIFT:
			slow = false;
			break;
		}
	}
}