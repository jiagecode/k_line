const MathUtil = {
	// 加
	add:(arg1, arg2)=>{
		let r1, r2, m;
		try { r1 = arg1.toString().split(".")[1].length } catch (e) { r1 = 0 }
		try { r2 = arg2.toString().split(".")[1].length } catch (e) { r2 = 0 }
		m = Math.pow(10, Math.max(r1, r2));
		return (arg1 * m + arg2 * m) / m;
	},
	// 减
	sub:(arg1, arg2)=>{
		let r1, r2, m, n;
		try { r1 = arg1.toString().split(".")[1].length } catch (e) { r1 = 0 }
		try { r2 = arg2.toString().split(".")[1].length } catch (e) { r2 = 0 }
		m = Math.pow(10, Math.max(r1, r2));
		//动态控制精度长度
		n = (r1 >= r2) ? r1 : r2;
		return Number(((arg1 * m - arg2 * m) / m).toFixed(n));
	},
	mul:(arg1, arg2)=>{
		let m = 0, s1 = arg1.toString(), s2 = arg2.toString();
		try {  m += s1.split(".")[1].length } catch (e) { }
		try { m += s2.split(".")[1].length } catch (e) { }
		return Number(s1.replace(".", "")) * Number(s2.replace(".", "")) / Math.pow(10, m);
	},
	div:(arg1, arg2)=>{
		let t1 = 0, t2 = 0, r1, r2;
		try { t1 = arg1.toString().split(".")[1].length } catch (e) { }
		try { t2 = arg2.toString().split(".")[1].length } catch (e) { }
		r1 = Number(arg1.toString().replace(".", ""));
		r2 = Number(arg2.toString().replace(".", ""));
		return (r1 / r2) * Math.pow(10, t2 - t1);
	},
	toFixed:(num, s)=>{
		let v2s = "1";
		for (let i = 0; i < s; i++) {
		    v2s += "0";
		}
		let v2 = parseInt(v2s);
		return (Math.round(num * v2) / v2) + '';
	},
	// 保留指定位数，不足补0
	getFloat:(number, n)=>{
		n = n ? parseInt(n) : 0;
		if(n <= 0) {
			return Math.round(number);
		}
		number = Math.round(number * Math.pow(10, n)) / Math.pow(10, n); //四舍五入
		number = Number(number).toFixed(n); //补足位数
		return number;
	},
}

export default MathUtil