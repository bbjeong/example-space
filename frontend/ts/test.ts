
function hello<T>(message: T): T {
    console.log(message);
    return message;
}

hello('Mark');
