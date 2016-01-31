/*
 * 全方位弾幕
 */
public class EveryShot extends Enemy{

	//回転速度
	int addAngle;

	EveryShot(EnemyData data){
		super(data);
		count = 0;
		addAngle = (int)data.exData[0];
		checkDelete = false;
	}

	//毎フレームの処理を行う
	public void process(){

		//速度分移動する
		x += vx;
		y += vy;

		//角度を更新する
		count += addAngle;
		count %= 360;

		//画面内に入ったことを判定する
		if(	checkInside() && checkDelete == false) {
			checkDelete = true;
		}

		//弾を描画する
		for(int i = 1;i <= bulletWay;i++){
			GameManager.enemyBullets.add(new EnemyBullet(x,y,
					count+(360/bulletWay)*i,
					bulletSpeed
			));

		}

		//画面外から出た場合削除フラグをtrueにする
		if(checkOutside() && checkDelete == true){
			deleteFlag = true;
		}

	}

	//描画処理を行う
	public void draw(){
		GameMain.graphics.setColor(225, 0, 0);
		GameMain.graphics.fillRect(x, y, 14, 15);
	}
}