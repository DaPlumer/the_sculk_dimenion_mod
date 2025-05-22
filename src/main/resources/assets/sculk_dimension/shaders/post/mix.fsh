#version 150

uniform sampler2D FirstSampler;
uniform sampler2D SecondSampler;
uniform float mixValue;
in vec2 texCoord;


out vec4 fragColor;
void main(){
    vec4 fc = texture(FirstSampler,texCoord);
    vec4 sc = texture(SecondSampler, texCoord);
    if(texCoord.y > mixValue){
        fragColor = fc;
    } else{
        fragColor = sc;
    }

}
