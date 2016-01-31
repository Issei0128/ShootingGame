/*
 * 死亡時に回復させる
 */
public class RecoverEnemy extends Enemy{

	//弾の距離の間隔を保存する
	float wayAngle;

	//発射レートを決める
	int shotRate;

	RecoverEnemy(EnemyData data){
		super(data);

		wayAngle = data.exData[0];
		shotRate = (int) data.exData[1];
		checkDelete = false;
	}

	//毎フレームの処理を行う
	public void process(){

		//速度分移動する
		x += vx;
		y += vy;

		//プレイヤーへの角度を計算する「
		float playerAngle;

		//プレイヤーへの角度を計算する
		playerAngle = (float)Math.atan2((double)(GameMain.player.y-y), (double)(GameMain.player.x-x));
		//PlayerRadianを角度に変更する
		playerAngle = GameMain.graphics.toAngle(playerAngle);

		//画面内に入ったことを判定する
		//画面内に入ったことで削除可能に変更する
		if(	checkInside() && checkDelete == false) {
			checkDelete = true;
		}

		//発射レートに合わせて弾を発射させる
		if(count % shotRate == 0){

			//弾を描画する
			float harfway = bulletWay / 2;

			if(bulletWay % 2==0){
				playerAngle -= ((wayAngle/2)+(wayAngle*(harfway-1)));
			}else{
				playerAngle -= wayAngle*harfway;
			}

			//弾を処理リストに追加する
			for(int i = 1;i <= bulletWay;i++){
				GameManager.enemyBullets.add(new EnemyBullet(x,y,playerAngle,bulletSpeed));
				playerAngle += wayAngle;
			}

			//画面外から出た場合削除フラグをtrueにする
			if(checkOutside() && checkDelete == true){
				deleteFlag = true;
			}
		}
		//カウンターを更新する
		count++;
	}

	//描画処理を行う
	public void draw(){
		GameMain.graphics.setColor(225, 64, 193);
		GameMain.graphics.fillRect(x, y, 14, 15);
	}
}