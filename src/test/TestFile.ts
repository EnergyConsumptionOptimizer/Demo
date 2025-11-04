const message = "hello world";

function greet(name: string) {
  console.log("Hi " + name);
}

greet(message);

export default {greet, message};
