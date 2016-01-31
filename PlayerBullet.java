/*
 * プレイヤーの弾のクラス
 */
public class PlayerBullet extends GameObject{

	//発射速度
	float vx,vy;
	PlayerBullet(float x,float y,float vx,float vy){
		super(x,y,true);
		this.vx = vx;
		this.vy = vy;
	}

	//弾の処理を行う
	public void process(){
		x += vx / GameMain.graphics.correctionWidth;
		y += vy / GameMain.graphics.correctionHeight;
	}

	//弾の描画を行う
	public void draw(){
		GameMain.graphics.setColor(50, 33, 220);
		GameMain.graphics.fillRect(x, y, 5, 5);
	}
}