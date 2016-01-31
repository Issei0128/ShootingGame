/*
 * 追尾弾幕
 */
public class AimShot extends Enemy{
	//弾の距離の間隔を保存する
	float wayAngle;

	//発射レートを決める
	int shotRate;

	AimShot(EnemyData data){
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

		//プレイヤーへの角度を計算する
		float PlayerAngle;
		PlayerAngle = (float)Math.atan2((double)(GameMain.player.y - y), (double)(GameMain.player.x - x));
		//PlayerRadianを角度に変更する
		PlayerAngle = GameMain.graphics.toAngle(PlayerAngle);

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
				PlayerAngle -= ((wayAngle/2)+(wayAngle*(harfway-1)));
			}else{
				PlayerAngle -= wayAngle*harfway;
			}

			//弾を処理リストに追加する
			for(int i = 1;i <= bulletWay;i++){
				GameManager.enemyBullets.add(new EnemyBullet(x,y,PlayerAngle,bulletSpeed));
				PlayerAngle += wayAngle;
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
		GameMain.graphics.setColor(225, 0, 0);
		GameMain.graphics.fillRect(x, y, 14, 15);
	}
}