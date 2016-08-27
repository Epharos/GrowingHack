package fr.epharos.growinghack.util;

import java.util.HashMap;
import java.util.Map;

public class Timer 
{
	private static Map<String, Timer> timers = new HashMap<String, Timer>();
	
	private float time = 0;
	public final float max;
	public final String key;
	
	public Timer(String key, float max)
	{
		this.max = max;
		this.key = key;
		Timer.timers.put(key, this);
	}

	public static Timer getTimer(String s)
	{
		return Timer.timers.get(s);
	}
	
	public static void update(float delta)
	{
		for(Map.Entry<String, Timer> entry : Timer.timers.entrySet())
		{
			Timer timer = ((Timer) entry.getValue());
			
			timer.time += delta;
			
			if(timer.time >= timer.max)
			{
				timer.execute();
				timer.time = timer.time - timer.max;
			}
		}
	}
	
	public void kill()
	{
		Timer.timers.remove(this.key);
	}
	
	public void execute()
	{
		
	}
}
