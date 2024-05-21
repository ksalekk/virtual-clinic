export const apiUrl = process.env.REACT_APP_API_URL || 'http:localhost:8080';

export const replaceUndefined = (obj, substitute) => {
	const isObject = (value) => value && typeof value === 'object' && !Array.isArray(value);

	const traverse = (obj) => {
		Object.keys(obj).forEach((key) => {
			if (obj.hasOwnProperty(key)) {
				if (obj[key] === undefined) {
					obj[key] = substitute;
				} else if (isObject(obj[key])) {
					traverse(obj[key]);
				}
			}
		})
	};

	traverse(obj);
	return obj;
};


export const formatDatetime = (datetime) => datetime.replace('T', ' ').slice(0, -3);






