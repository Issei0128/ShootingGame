/*
 * 敵弾のクラス
 * すべての敵はこのクラスを使い敵弾を生成する
 */
public class EnemyBullet extends GameObject{
	//発射角度,発射スピード
	float radian,shotSpeed;

	//敵クラスからデータを受け取るコンストラクタ
	EnemyBullet(float x,float y,float Radian,float ShotSpeed){
		super(x,y,true);

		this.radian = Radian;
		this.shotSpeed = ShotSpeed;
	}

	//敵弾の制御情報を変更する
	public void setBuleet(float Radian,float ShotSpeed){
		this.radian = Radian;
		this.shotSpeed = ShotSpeed;
	}

	//敵弾の処理を行う
	public void process(){
		x += (shotSpeed / GameMain.graphics.correctionWidth) *(float)(Math.cos(GameMain.graphics.toRadian(radian)));
		y += (shotSpeed / GameMain.graphics.correctionHeight) *(float)(Math.sin(GameMain.graphics.toRadian(radian)));
	}

	//敵弾の描画を行う
	public void draw(){
		GameMain.graphics.setColor(106, 221, 59);
		GameMain.graphics.fillOval(x, y, 10, 10);

	}
}