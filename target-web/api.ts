import moment from "moment";
import {UAParser} from "ua-parser-js";

const baseUrl = "api2.cubos.io/anaiti-user";

export interface User {
  id: string;
  name: string;
}

export async function getCurrentUser(): Promise<User> {
  const ret = await makeRequest({name: "getCurrentUser", args: {}});
  return {
    id: ret.id,
    name: ret.name,
  };
}

export async function logIn(id: string, password: string): Promise<User> {
  const args = {
    id: id,
    password: password,
  };
  const ret = await makeRequest({name: "logIn", args});
  return {
    id: ret.id,
    name: ret.name,
  };
}

export async function logOut(): Promise<void> {
  await makeRequest({name: "logOut", args: {}});
  return undefined;
}

//////////////////////////////////////////////////////

function device() {
  const parser = new UAParser();
  parser.setUA(navigator.userAgent);
  const agent = parser.getResult();
  const me = document.currentScript as HTMLScriptElement;
  const device: any = {
    platform: "web",
    platformVersion: `${agent.browser.name} ${agent.browser.version} on ${agent.os.name} ${agent.os.version}`,
    version: me ? me.src : "",
    language: navigator.language
  };
  const deviceId = localStorage.getItem("deviceId");
  if (deviceId)
    device.id = deviceId;
  return device;
}

function randomBytesHex(len: number) {
  let hex = "";
  for (let i = 0; i < 2 * len; ++i)
    hex += "0123456789abcdef"[Math.floor(Math.random()*16)];
  return hex;
}

async function makeRequest({name, args}: {name: string, args: any}) {
  return new Promise<any>((resolve, reject) => {
    const req = new XMLHttpRequest();
    req.open("POST", "https://" + baseUrl + "/" + name);
    const body = {
      id: randomBytesHex(16),
      device: device(),
      name: name,
      args: args
    };
    req.onreadystatechange = () => {
      if (req.readyState !== 4) return;
      try {
        const response = JSON.parse(req.responseText);
        localStorage.setItem("deviceId", response.deviceId);
        if (response.ok) {
          resolve(response.result);
        } else {
          reject(response.error);
        }
      } catch (e) {
        console.error(e);
        reject({type: "ServerError", message: e.toString()});
      }
    };
    req.send(JSON.stringify(body));
  });
}
