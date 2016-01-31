import java.util.ArrayList;

public class ExplosionEffect extends GameObject{

	ArrayList<ExplosionParticle> particle;

	//カウンター
	int count = 0;

	//色
	int r,g,b;

	//パーティクルのサイズ
	int particleSize;

	//回転速度
	float rotationalSpeed;


	ExplosionEffect(float x,float y,int space){
		super(x,y,false);
		particle = new ArrayList<ExplosionParticle>();

		for(float i = 0;i < 360;i += 360 / (float)space){
			particle.add(new ExplosionParticle(x,y,i,0.005f));
		}

		for(float i = 0;i < 360;i += 360 / (float)space){
			particle.add(new ExplosionParticle(x,y,i+90,0.01f));
		}


		//描画色を指定する
		r = 207;
		g = 32;
		b = 32;

		//パーティクルサイズを決める
		particleSize = 25;

		rotationalSpeed = 6.0f;
	}

	ExplosionEffect(float x,float y,int space,int R,int G,int B,int ParticleSize,float Speed){
		super(x,y,false);
		particle = new ArrayList<ExplosionParticle>();

		for(float i = 0;i < 360;i += 360 / (float)space){
			particle.add(new ExplosionParticle(x,y,i,0.005f));
		}

		for(float i = 0;i < 360;i += 360 / (float)space){
			particle.add(new ExplosionParticle(x,y,i+90,0.01f));
		}
		//描画色を指定する
		this.r = R;
		this.g = G;
		this.b = B;

		//パーティクルサイズを決める
		this.particleSize = ParticleSize;

		//回転速度
		rotationalSpeed = Speed;
	}


	//毎フレームの処理を行う
	public void process(){

		for(int i = 0;i < particle.size();i++){
			GameObject obj = particle.get(i);
			obj.process();

			if(obj.deleteFlag == true){
				particle.remove(i);
				deleteFlag = true;
			}
		}
		count++;

	};
	//描画処理を行う
	public void draw(){
		for(int i = 0;i < particle.size();i++){
			GameObject obj = particle.get(i);
			obj.draw();
		}
	};

	//爆発時のパーティクル？クラス
	public class ExplosionParticle extends GameObject{
		//発射角度,発射スピード
		float Radian,ShotSpeed;
		float StartRadian;

		ExplosionParticle(float x,float y,float Radian,float ShotSpeed){
			super(x,y,false);
			this.Radian = Radian;
			this.StartRadian = Radian;
			this.ShotSpeed = ShotSpeed;
		}

		public void SetBuleet(float Radian,float ShotSpeed){
			this.Radian = Radian;
			this.ShotSpeed = ShotSpeed;
		}

		//処理を行う
		public void process(){
			SetBuleet(Radian + rotationalSpeed,ShotSpeed);
			x += (ShotSpeed / GameMain.graphics.correctionWidth) *(float)(Math.cos(GameMain.graphics.toRadian(Radian)));
			y += (ShotSpeed / GameMain.graphics.correctionHeight) *(float)(Math.sin(GameMain.graphics.toRadian(Radian)));

			//1回転が終了した時点で削除する
			if(deleteFlag == false && Radian - StartRadian > 360 ){
				deleteFlag = true;
			}

		}

		//描画を行う
		public void draw(){
			GameMain.graphics.setColor(r, g, b);
			GameMain.graphics.centeringfillOval(x, y, particleSize, particleSize);

		}
	}
}