const message = "hello world";

function greet(name: string) {
  console.log("Hi " + name);
}

greet(message);

const unused = 123;

export default { greet, message };
