const request = require("supertest");
const baseURL = 'http://0.0.0.0:5000';

describe('GET /sumar/:n1/:n2', () => {
  it("should return 200", async () => {
    const response = await request(baseURL).get("/sumar/1/2");
    expect(response.statusCode).toBe(200);
    expect(response.body.error).toBe(undefined);
  })

  it("should return text with addition", async () => {
    const response = await request(baseURL).get("/sumar/1/2");
    expect(response.text).toBe("La suma es :3");
  })
})