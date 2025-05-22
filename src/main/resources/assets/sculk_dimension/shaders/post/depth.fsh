#version 150

uniform sampler2D InSampler;
in vec2 texCoord;


out vec4 fragColor;
void main(){
    vec4 diffuseColor = texture(InSampler, texCoord);
    float d = 10*(1- diffuseColor.r);
    fragColor = vec4(d,d,d, 1.0);
}
