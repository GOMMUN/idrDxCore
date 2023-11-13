package com.idr.pdd.common;

import java.util.ArrayList;

public class NelsonRulesProcessor
{
	private double UCL = Double.MAX_VALUE;
	private double LCL = -Double.MAX_VALUE;
	private double CL = 0;

	private double Sigma = 0;
	private double US1 = 0;
	private double US2 = 0;
	private double LS1 = 0;
	private double LS2 = 0;

	private boolean rule1_use_yn = false;
	private boolean rule2_use_yn = false;
	private boolean rule3_use_yn = false;
	private boolean rule4_use_yn = false;
	private boolean rule5_use_yn = false;
	private boolean rule6_use_yn = false;
	private boolean rule7_use_yn = false;
	private boolean rule8_use_yn = false;

	private ArrayList<Double> aqEvent = null;
	private int event_count = 0;

	public int rule2_length = 7;	//	>= 7
	public int rule3_length = 5;	//	>= 5
	public int rule4_length = 10;	//	>= 10
	public int rule5_length = 3;	//	>= 3
	public int rule5_limit = 2;	//	>= 2
	public int rule6_length = 4;	//	>= 4
	public int rule6_limit = 3;	//	>= 3
	public int rule7_length = 11;	//	>= 11
	public int rule8_length = 6;	//	>= 6

	public NelsonRulesProcessor(double UCL, double LCL, int rule2_length, int rule3_length, int rule4_length, int rule5_length, int rule5_limit, int rule6_length, int rule6_limit, int rule7_length, int rule8_length)
	{
		this.rule2_length = rule2_length;
		this.rule3_length = rule3_length;
		this.rule4_length = rule4_length;
		this.rule5_length = rule5_length;
		this.rule5_limit = rule5_limit;
		this.rule6_length = rule6_length;
		this.rule6_limit = rule6_limit;
		this.rule7_length = rule7_length;
		this.rule8_length = rule8_length;

		init(UCL, LCL);
	}

	public void init(double UCL, double LCL)
	{
		this.UCL = UCL;
		this.LCL = LCL;
		CL = (UCL + LCL) / 2;

		Sigma = (UCL - LCL) / 6;
		US1 = CL + Sigma;
		US2 = CL + Sigma * 2;
		LS1 = CL - Sigma;
		LS2 = CL - Sigma * 2;
	}

	public void setEvent(ArrayList<Double> aqEvent)
	{
		this.aqEvent = aqEvent;
		event_count = aqEvent.size();
	}

	public boolean rule1()	//
	{
		if (event_count < 1)
		{
			return false;
		}

		double value = aqEvent.get(event_count - 1);

		if (value > UCL || value < LCL)
		{
			return true;
		}

		return false;
	}

	public boolean rule2()	//
	{
		if (event_count < rule2_length)
		{
			return false;
		}

		double value = aqEvent.get(event_count - rule2_length);

		if (value == CL)
		{
			return false;
		}

		boolean isUpper = (value > CL) ? true: false;

		for (int index = event_count - (rule2_length - 1); index < event_count; ++index)
		{
			value = aqEvent.get(index);

			if (isUpper)
			{
				if  (value <= CL)
				{
					return false;
				}
			} else
			{
				if (value >= CL)
				{
					return false;
				}
			}
		}

		return true;
	}

	public boolean rule3()
	{
		if (event_count < rule3_length)
		{
			return false;
		}

		double preValue = aqEvent.get(event_count - rule3_length);
		double value = aqEvent.get(event_count - (rule3_length - 1));

		if (preValue == value)
		{
			return false;
		}

		boolean increase = (preValue < value) ? true : false;

		preValue = value;

		for (int index = event_count - (rule3_length - 2); index < event_count; ++index)
		{
			value = aqEvent.get(index);

			if (increase)
			{
				if (value <= preValue)
				{
					return false;
				}
			} else
			{
				if (value >= preValue)
				{
					return false;
				}
			}

			preValue = value;
		}

		return true;
	}

	public boolean rule4()
	{
		if (event_count < rule4_length)
		{
			return false;
		}

		double preValue = aqEvent.get(event_count - rule4_length);
		double value = aqEvent.get(event_count - (rule4_length - 1));

		if (preValue == value)
		{
			return false;
		}

		boolean isUpper = (preValue < value) ? true : false;

		preValue = value;

		for (int index = event_count - (rule4_length - 2); index < event_count; ++index)
		{
			value = aqEvent.get(index);

			if (isUpper)
			{
				if (value >= preValue)
				{
					return false;
				}
			} else
			{
				if (value <= preValue)
				{
					return false;
				}
			}

			preValue = value;
			isUpper = !isUpper;
		}

		return true;
	}

	public boolean rule5()	//
	{
		if (event_count < rule5_limit)
		{
			return false;
		}

		double[] arrValue = new double[rule5_length];

		for (int i = rule5_length, j = 0, index = 0; i > j; i--, index++)
		{
			int k = event_count - i;

			if (k > -1)
			{
				arrValue[index] = aqEvent.get(k);
			} else
			{
				arrValue[index] = CL;
			}
		}

		int score = 0;

		for (int i = 0, j = rule5_length; i < j; i++)
		{
			if (arrValue[i] > US2)
			{
				score += 1;
			}

			if (score >= rule5_limit)
			{
				return true;
			}
		}

		score = 0;

		for (int i = 0, j = rule5_length; i < j; i++)
		{
			if (arrValue[i] < LS2)
			{
				score += -1;
			}

			if (score <= -rule5_limit)
			{
				return true;
			}
		}

		return false;
	}

	public boolean rule6()	//
	{
		if (event_count < rule6_limit)
		{
			return false;
		}

		double[] arrValue = new double[rule6_length];

		for (int i = rule6_length, j = 0, index = 0; i > j; i--, index++)
		{
			int k = event_count - i;

			if (k > -1)
			{
				arrValue[index] = aqEvent.get(k);
			} else
			{
				arrValue[index] = CL;
			}
		}

		int score = 0;

		for (int i = 0, j = rule6_length; i < j; i++)
		{
			if (arrValue[i] > US1)
			{
				score += 1;
			}

			if (score >= rule6_limit)
			{
				return true;
			}
		}

		score = 0;

		for (int i = 0, j = rule6_length; i < j; i++)
		{
			if (arrValue[i] < LS1)
			{
				score += -1;
			}

			if (score <= -rule6_limit)
			{
				return true;
			}
		}

		return false;
	}

	public boolean rule7()
	{
		if (event_count < rule7_length)
		{
			return false;
		}

		for (int index = event_count - rule7_length; index < event_count; ++index)
		{
			double value = aqEvent.get(index);

			if (value > US1 || value < LS1)
			{
				return false;
			}
		}

		return true;
	}

	public boolean rule8()
	{
		if (event_count < rule8_length)
		{
			return false;
		}

		boolean upper = false;
		boolean lower = false;

		for (int index = event_count - rule8_length; index < event_count; ++index)
		{
			double value = aqEvent.get(index);

			if (value < US1 && value > LS1)
			{
				return false;
			}

			if (!upper)
			{
				if (value > US1)
				{
					upper = true;
				}
			}

			if (!lower)
			{
				if (value < LS1)
				{
					lower = true;
				}
			}
		}

		if (upper && lower)
		{
			return true;
		}

		return false;
	}

	private String getData(int data_count)
	{
		StringBuilder sb = new StringBuilder();

		for (int i = 1, j = data_count + 1; i < j; i++)
		{
			double value = aqEvent.get(event_count - i);
			sb.append(value);

			if (i < data_count)
			{
				sb.append(",");
			}
		}

		return sb.toString();
	}
}

